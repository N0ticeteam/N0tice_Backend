package N0tice_Project.N0tice_BE.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Configuration
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String JWT_SECRET_KEY; //시크릿 키
    @Value("${jwt.access-token.expiration-time}")
    private long JWT_ACCESS_TOKEN_EXPIRATION_TIME;
    @Value("${jwt.refresh-token.expiration-time}")
    private long JWT_REFRESH_TOKEN_EXPIRATION_TIME;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.JWT_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // access token 생성
    public String createAccessToken(String userId) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();

        log.info("액세스 토큰이 발행되었습니다. 사용자 ID: {}", userId);
        return token;
    }

    // refresh token 생성
    public String createRefreshToken() {
        String token = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();

        log.info("리프레쉬 토큰이 발행되었습니다.");
        return token;
    }

    // 토큰에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            String userId = claims.getSubject();
            log.debug("토큰에서 사용자 ID 추출: {}", userId);
            return userId;
        } catch (Exception e) {
            log.error("토큰에서 사용자 ID 추출 실패: {}", e.getMessage());
            return null;
        }
    }

}
