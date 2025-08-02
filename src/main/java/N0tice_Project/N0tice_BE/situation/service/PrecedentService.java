package N0tice_Project.N0tice_BE.situation.service;

import N0tice_Project.N0tice_BE.global.exception.GeneralException;
import N0tice_Project.N0tice_BE.global.external.dto.Item;
import N0tice_Project.N0tice_BE.global.external.dto.PublicApiResponse;
import N0tice_Project.N0tice_BE.global.status.ErrorStatus;
import N0tice_Project.N0tice_BE.situation.domain.Situation;
import N0tice_Project.N0tice_BE.situation.dto.PrecedentDetailResponse;
import N0tice_Project.N0tice_BE.situation.dto.PrecedentTitleResponse;
import N0tice_Project.N0tice_BE.situation.repository.SituationRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrecedentService {
    private final SituationRepository situationRepository;
    private final WebClient webClient;

    @Value("${public-api.service-key}")
    private String serviceKey;

    @Value("${public-api.base-url}")
    private String baseUrl;

    @Transactional(readOnly = true)
    public List<PrecedentTitleResponse> findPrecedentsForSituation(Long situationId) {
        Situation situation = situationRepository.findById(situationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._SITUATION_NOT_FOUND));

        List<Item> allItems = searchAndMergeLostCases(situation.getKindb(), situation.getKindc());

        return allItems.stream()
                .map(PrecedentTitleResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PrecedentDetailResponse findPrecedentDetail(Long situationId, String caseNumber) {

        Situation situation = situationRepository.findById(situationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._SITUATION_NOT_FOUND));

        List<Item> allItems = searchAndMergeLostCases(situation.getKindb(), situation.getKindc());

        // 사건 번호가 일치하는 첫 번째 항목
        return allItems.stream()
                .filter(item -> item.getAccnum().equals(caseNumber))
                .findFirst()
                .map(PrecedentDetailResponse::from)
                .orElseThrow(() -> new GeneralException(ErrorStatus._PRECEDENT_NOT_FOUND));
    }


    private List<Item> searchAndMergeLostCases(String kindb, String kindc) {

        // "기각"과 "각하"에 대한 API 호출을 각각 Mono로 정의
        Mono<List<Item>> rejectionMono = fetchPrecedents("기각", kindb, kindc);
        Mono<List<Item>> dismissalMono = fetchPrecedents("각하", kindb, kindc);

        // Mono.zip을 사용하여 두 Mono를 병렬로 실행, 두 결과가 모두 도착하면 하나로 합침
        return Mono.zip(rejectionMono, dismissalMono)
                .map(tuple -> {
                    List<Item> rejectionItems = tuple.getT1();
                    List<Item> dismissalItems = tuple.getT2();

                    return Stream.concat(rejectionItems.stream(), dismissalItems.stream())
                            .collect(Collectors.toList());
                })
                .block();
    }

    @Cacheable(value = "precedents", key = "#kinda + '-' + #kindb + '-' + #kindc")
    public Mono<List<Item>> fetchPrecedents(String kinda, String kindb, String kindc) {
        log.info("API 호출: kinda={}, kindb={}, kindc={}", kinda, kindb, kindc);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("ServiceKey", URLEncoder.encode(serviceKey, StandardCharsets.UTF_8))
                .queryParam("numOfRows", 5)
                .queryParam("pageNo", 1);

        // kinda는 항상 "기각" 또는 "각하"
        uriBuilder.queryParam("kindA", URLEncoder.encode(kinda, StandardCharsets.UTF_8));
        uriBuilder.queryParam("kindB", URLEncoder.encode(kindb, StandardCharsets.UTF_8));
        // kindc는 null일 수 있으므로 조건부로 추가
        if (StringUtils.hasText(kindc)) {
            uriBuilder.queryParam("kindC", URLEncoder.encode(kindc, StandardCharsets.UTF_8));
        }

        URI uri = uriBuilder.build(true).toUri();
        log.info("Final API Request URL: {}", uri.toString());

        return webClient.get().uri(uri)
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String.class)
                .map(xmlString -> {
                    try {
                        XmlMapper xmlMapper = new XmlMapper();
                        PublicApiResponse response = xmlMapper.readValue(xmlString, PublicApiResponse.class);

                        return (response != null && response.getBody() != null && response.getBody().getItems() != null) ?
                                response.getBody().getItems() : Collections.<Item>emptyList();
                    } catch (Exception e) {
                        throw new RuntimeException("XML 수동 파싱 중 오류 발생", e);
                    }
                })
                .retryWhen(reactor.util.retry.Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> throwable instanceof WebClientRequestException)
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                            throw new RuntimeException("API 재시도 모두 실패", retrySignal.failure());
                        }));
    }
}
