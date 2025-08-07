package N0tice_Project.N0tice_BE.auth.handler;

import N0tice_Project.N0tice_BE.auth.config.JwtTokenProvider;
import N0tice_Project.N0tice_BE.auth.info.GoogleUserInfo;
import N0tice_Project.N0tice_BE.auth.info.NaverUserInfo;
import N0tice_Project.N0tice_BE.auth.domain.UserSocialAuth;
import N0tice_Project.N0tice_BE.auth.info.OAuth2UserInfo;
import N0tice_Project.N0tice_BE.auth.repository.RedisRepository;
import N0tice_Project.N0tice_BE.auth.repository.UserSocialAuthRepository;
import N0tice_Project.N0tice_BE.user.domain.User;
import N0tice_Project.N0tice_BE.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${jwt.redirect}")
    private String JWT_REDIRECT;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserSocialAuthRepository userSocialAuthRepository;
    private final UserRepository userRepository;
    private final RedisRepository redisRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String provider = oauthToken.getAuthorizedClientRegistrationId();
        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();

        OAuth2UserInfo oAuth2UserInfo = switch (provider) {
            case "google" ->
                new GoogleUserInfo(oauthToken.getPrincipal().getAttributes());
            case "naver" -> new NaverUserInfo((Map<String, Object>) attributes.get("response"));
            default -> throw new RuntimeException("지원하지 않는 OAuth 제공자입니다.");
        };

        String providerUserId = oAuth2UserInfo.getName();

        // 소셜 인증 정보 조회 또는 생성
        UserSocialAuth socialAuth = userSocialAuthRepository
                .findByProviderAndSocialId(provider, providerUserId)
                .orElseGet(() -> {
                    log.info("신규 유저입니다. 회원가입을 진행합니다.");

                    User newUser = User.builder()
                            .username(oAuth2UserInfo.getName() != null ? oAuth2UserInfo.getName() : "사용자")
                            .email(oAuth2UserInfo.getEmail() != null ? oAuth2UserInfo.getEmail() : provider + "_user_" + System.currentTimeMillis() + "@example.com")
                            .createdAt(LocalDateTime.now())
                            .build();
                    userRepository.save(newUser);
                    
                    log.info("신규 사용자 생성 완료 - ID: {}, 이름: {}, 이메일: {}", 
                            newUser.getUserId(), newUser.getUsername(), newUser.getEmail());

                    return userSocialAuthRepository.save(
                            UserSocialAuth.builder()
                                    .providerName(provider)
                                    .providerUserId(providerUserId)
                                    .user(newUser)
                                    .connectedAt(LocalDateTime.now())
                                    .build()
                    );
                });

        // 사용자 ID를 문자열로 변환하여 토큰 생성
        String userId = socialAuth.getUser().getUserId().toString();
        redisRepository.deleteRefreshToken(userId);

        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken();
        redisRepository.saveRefreshToken(userId, refreshToken);

        // 사용자 정보를 함께 전달 (URL 파라미터로)
        String encodedAccessToken = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);
        String encodedRefreshToken = URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);
        String encodedUserId = URLEncoder.encode(userId, StandardCharsets.UTF_8);
        String encodedUsername = URLEncoder.encode(socialAuth.getUser().getUsername(), StandardCharsets.UTF_8);
        
        String redirectUrl = String.format("%s?accessToken=%s&refreshToken=%s&userId=%s&username=%s",
                JWT_REDIRECT, encodedAccessToken, encodedRefreshToken, encodedUserId, encodedUsername);

        log.info("로그인 성공 - 사용자 ID: {}, 이름: {}", userId, socialAuth.getUser().getUsername());
        log.info("앱으로 리다이렉트: {}", redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
