package N0tice_Project.N0tice_BE.auth.info;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class GoogleUserInfo implements OAuth2UserInfo{
    private Map<String, Object> attributes;

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() { return attributes.get("email").toString(); }
}
