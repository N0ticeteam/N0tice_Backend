package N0tice_Project.N0tice_BE.worklog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class WorklogRequest {
    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate logDate;

    private String title;

    private String content;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private String managerName;

    private String agentName;

    private String accidentRelatedNotes;
}
