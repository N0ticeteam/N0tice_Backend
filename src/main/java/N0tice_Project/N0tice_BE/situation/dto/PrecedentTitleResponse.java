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
    private String courtName;
    private String caseNumber;

    public static PrecedentTitleResponse from(Item item) {

        String conclusion = "[" + item.getKinda() + "] ";

        String originalTitle = item.getTitle();

        String newTitle = conclusion + originalTitle;

        return new PrecedentTitleResponse(
                newTitle,
                item.getCourtname(),
                item.getAccnum()
        );
    }
}
