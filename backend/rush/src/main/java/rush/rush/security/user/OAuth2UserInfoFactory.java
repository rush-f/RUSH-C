package rush.rush.security.user;

import java.util.Map;
import rush.rush.domain.AuthProvider;
import rush.rush.security.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(registrationId + " 로그인은 지원하지 않습니다.");
        }
    }
}