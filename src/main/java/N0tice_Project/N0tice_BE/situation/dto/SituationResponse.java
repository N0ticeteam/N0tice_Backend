package N0tice_Project.N0tice_BE.situation.dto;

import N0tice_Project.N0tice_BE.situation.domain.Situation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SituationResponse {
    private Long inputId;
    private Long userId;
    private String situationType;
    private String additionalInfoText;
    private String inputKeywords;
    private LocalDateTime inputTimestamp;

    public static SituationResponse of(Situation entity) {
        return new SituationResponse(
                entity.getInputId(),
                entity.getUser().getUserId(),
                entity.getSituationType(),
                entity.getAdditionalInfoText(),
                entity.getInputKeywords(),
                entity.getInputTimestamp()
        );
    }
}
