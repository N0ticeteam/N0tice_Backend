package N0tice_Project.N0tice_BE.situation.domain;

import N0tice_Project.N0tice_BE.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "user_situation_input")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Situation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inputId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String kindb; // 검색 조건 B

    @Column(nullable = true)
    private String kindc; // 검색 조건 C

    @Column(updatable = false)
    private LocalDateTime createdAt;

}
