package N0tice_Project.N0tice_BE.situation.dto;

import N0tice_Project.N0tice_BE.global.external.dto.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "판례 제목 응답 DTO")
public class PrecedentTitleResponse {

    private String title;

    public static PrecedentTitleResponse from(Item item) { return new PrecedentTitleResponse(item.getTitle()); }

}
