package N0tice_Project.N0tice_BE.ai.domain;

import N0tice_Project.N0tice_BE.situation.domain.Situation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aiPredictionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "input_id")
    private Situation input;

    @Column(nullable = false)
    private BigDecimal aiPredictedLossProbability;

    private LocalDateTime predictionTimestamp;
    private String title;
    private String message;
}
