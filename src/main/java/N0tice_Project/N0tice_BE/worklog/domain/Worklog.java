package N0tice_Project.N0tice_BE.worklog.domain;

import N0tice_Project.N0tice_BE.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Worklog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate logDate;
    private String title;
    private String content;
    private LocalTime startTime;
    private LocalTime endTime;
    private String managerName;
    private String agentName;
    private String accidentRelatedNotes;
    private LocalDateTime createdAt;
}
