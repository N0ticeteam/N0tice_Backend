package N0tice_Project.N0tice_BE.worklog.domain;

import N0tice_Project.N0tice_BE.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(name = "work_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Worklog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "manager_name", length = 100)
    private String managerName;

    @Column(name = "agent_name", length = 100)
    private String agentName;

    @Column(name = "accident_related_notes", columnDefinition = "TEXT")
    private String accidentRelatedNotes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
