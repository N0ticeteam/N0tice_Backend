package N0tice_Project.N0tice_BE.auth.info;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    @Override
    public String getProviderUserId() {
        return (String) attributes.get("name");
    }
}
