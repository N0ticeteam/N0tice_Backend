package N0tice_Project.N0tice_BE.situation.service;

import N0tice_Project.N0tice_BE.global.exception.GeneralException;
import N0tice_Project.N0tice_BE.global.status.ErrorStatus;
import N0tice_Project.N0tice_BE.situation.domain.Situation;
import N0tice_Project.N0tice_BE.situation.dto.SituationRequest;
import N0tice_Project.N0tice_BE.situation.dto.SituationResponse;
import N0tice_Project.N0tice_BE.situation.repository.SituationRepository;
import N0tice_Project.N0tice_BE.user.domain.User;
import N0tice_Project.N0tice_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class SituationService {

    private final SituationRepository situationRepository;
    private final UserRepository userRepository;

    @Transactional
    public SituationResponse createSituation(SituationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._USER_NOT_FOUND));

        Situation situation = Situation.builder()
                .user(user)
                .kindb(request.getKindb())
                .kindc(request.getKindc())
                .createdAt(LocalDateTime.now())
                .build();

        Situation savedSituation = situationRepository.save(situation);

        return SituationResponse.fromEntity(savedSituation);
    }
}
