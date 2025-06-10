package N0tice_Project.N0tice_BE.auth.repository;

import N0tice_Project.N0tice_BE.auth.domain.UserSocialAuth;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserSocialAuthRepository extends JpaRepository<UserSocialAuth, Long> {
    // provider와 socialId로 인증 정보 조회
    @Query("SELECT usa FROM UserSocialAuth usa JOIN FETCH usa.user WHERE usa.providerName = :providerName AND usa.providerUserId = :providerUserId")
    Optional<UserSocialAuth> findByProviderAndSocialId(
            @Param("providerName") String providerName,      // <--- 이 부분이 핵심입니다.
            @Param("providerUserId") String providerUserId // <--- 이 부분이 핵심입니다.
    );
}
