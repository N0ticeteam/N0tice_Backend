package N0tice_Project.N0tice_BE.global.external.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JacksonXmlRootElement(localName = "response")
public class PublicApiResponse {
    private Header header;
    private Body body;
}
