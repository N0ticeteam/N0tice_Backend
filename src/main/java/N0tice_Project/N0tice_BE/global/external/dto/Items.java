package N0tice_Project.N0tice_BE.global.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Items {

    // List<Item>으로 선언하면 Jackson 라이브러리가 유연하게 처리
    @JsonProperty("item")
    private List<Item> itemList;
}