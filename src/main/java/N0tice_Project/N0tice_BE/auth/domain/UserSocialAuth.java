package N0tice_Project.N0tice_BE.auth.domain;

import N0tice_Project.N0tice_BE.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSocialAuth {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long socialAuthId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String providerName;

    @Column(nullable = false)
    private String providerUserId;

    private LocalDateTime connectedAt;
    private String accessToken;
    private String refreshToken;
}
