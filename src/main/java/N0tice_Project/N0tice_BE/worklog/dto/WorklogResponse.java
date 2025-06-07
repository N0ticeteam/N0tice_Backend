package N0tice_Project.N0tice_BE.worklog.dto;

import N0tice_Project.N0tice_BE.worklog.domain.Worklog;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class WorklogResponse {
    private Long logId;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate logDate;

    private String content;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private String managerName;

    private String agentName;

    private String accidentRelatedNotes;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public static WorklogResponse of(Worklog worklog) {
        return new WorklogResponse(
                worklog.getLogId(),
                worklog.getTitle(),
                worklog.getLogDate(),
                worklog.getContent(),
                worklog.getStartTime(),
                worklog.getEndTime(),
                worklog.getManagerName(),
                worklog.getAgentName(),
                worklog.getAccidentRelatedNotes(),
                worklog.getCreatedAt()
        );
    }
}
