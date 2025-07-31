package N0tice_Project.N0tice_BE.situation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SituationRequest {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "검색 조건 B", example = "보험금여지급수등")
    private String kindb;

    @Schema(description = "검색 조건 C", example = "보험금여지급수등")
    private String kindc;
}
