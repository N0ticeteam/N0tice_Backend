package N0tice_Project.N0tice_BE;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class N0ticeBeApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMalformed()
				.ignoreIfMissing()
				.load();

		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("REDIS_HOST", dotenv.get("REDIS_HOST"));
		System.setProperty("REDIS_PORT", dotenv.get("REDIS_PORT"));
		System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
		System.setProperty("JWT_REDIRECT", dotenv.get("JWT_REDIRECT"));
		System.setProperty("JWT_ACCESS_TOKEN_EXPIRATION_TIME", dotenv.get("JWT_ACCESS_TOKEN_EXPIRATION_TIME"));
		System.setProperty("JWT_REFRESH_TOKEN_EXPIRATION_TIME", dotenv.get("JWT_REFRESH_TOKEN_EXPIRATION_TIME"));
		System.setProperty("OAUTH_NAVER_CLIENT_ID", dotenv.get("OAUTH_NAVER_CLIENT_ID"));
		System.setProperty("OAUTH_NAVER_CLIENT_SECRET", dotenv.get("OAUTH_NAVER_CLIENT_SECRET"));
		System.setProperty("OAUTH_NAVER_REDIRECT_URI", dotenv.get("OAUTH_NAVER_REDIRECT_URI"));
		System.setProperty("SSL_KEYSTORE_PASSWORD", dotenv.get("SSL_KEYSTORE_PASSWORD"));
		System.setProperty("SSL_KEYSTORE_TYPE", dotenv.get("SSL_KEYSTORE_TYPE"));
		SpringApplication.run(N0ticeBeApplication.class, args);
	}

}
