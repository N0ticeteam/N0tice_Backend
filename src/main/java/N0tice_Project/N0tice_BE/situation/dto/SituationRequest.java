package N0tice_Project.N0tice_BE.situation.dto;

import lombok.Data;

@Data
public class SituationRequest {
    private Long userId;
    private String situationType;
    private String additionalInfoText;
    private String inputKeywords;
}
