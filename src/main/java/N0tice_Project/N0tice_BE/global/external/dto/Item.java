package N0tice_Project.N0tice_BE.global.external;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private String accnum;
    private String courtname;
    private String kinda;
    private String kindb;
    private String kindc;
    private String noncontent; // 상세 내용에 해당
    private String title;      // 제목에 해당
}
