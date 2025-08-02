package N0tice_Project.N0tice_BE.situation.dto;

import N0tice_Project.N0tice_BE.situation.domain.Situation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "생성된 판례 검색 기록 응답 DTO")
public class SituationResponse {
    private Long id;
    private Long userId;
    private String kindb;
    private String kindc;
    private LocalDateTime createdAt;

    public static SituationResponse fromEntity(Situation situation) {
        return SituationResponse.builder()
                .id(situation.getInputId())
                .userId(situation.getUser().getUserId())
                .kindb(situation.getKindb())
                .kindc(situation.getKindc())
                .createdAt(situation.getCreatedAt())
                .build();
    }
}
