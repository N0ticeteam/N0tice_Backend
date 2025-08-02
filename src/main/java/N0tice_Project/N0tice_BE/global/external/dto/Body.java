package N0tice_Project.N0tice_BE.global.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Body {
    @JacksonXmlElementWrapper(localName = "items") // 이 리스트가 <items> 태그로 감싸여 있음
    @JacksonXmlProperty(localName = "item")      // 리스트의 각 항목이 <item> 태그
    private List<Item> items;
    private String numOfRows;
    private String pageNo;
    private String totalCount;
}