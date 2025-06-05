package N0tice_Project.N0tice_BE.auth.repository;

import N0tice_Project.N0tice_BE.auth.domain.UserSocialAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserSocialAuthRepository extends JpaRepository<UserSocialAuth, Long> {
    // provider와 socialId로 인증 정보 조회
    @Query("SELECT usa FROM UserSocialAuth usa JOIN FETCH usa.user WHERE usa.providerName = :providerName AND usa.providerUserId = :providerUserId")
    Optional<UserSocialAuth> findByProviderAndSocialId(String providerName, String providerUserId);
}
