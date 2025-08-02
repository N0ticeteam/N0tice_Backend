package N0tice_Project.N0tice_BE.situation.dto;

import N0tice_Project.N0tice_BE.global.external.dto.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "판례 내용 상세 응답 DTO")
public class PrecedentDetailResponse {
    private String title;
    private String content;

    public static PrecedentDetailResponse from(Item item) {
        return new PrecedentDetailResponse(item.getTitle(), item.getNoncontent());
    }
}
