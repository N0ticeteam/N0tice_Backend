package N0tice_Project.N0tice_BE.worklog.dto;

import N0tice_Project.N0tice_BE.worklog.domain.Worklog;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class MonthlyWorklogResponse {

    private Long logId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate logDate;

    private String title;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    public static MonthlyWorklogResponse from(Worklog wl) {
        return new MonthlyWorklogResponse(
                wl.getLogId(),
                wl.getLogDate(),
                wl.getTitle(),
                wl.getStartTime(),
                wl.getEndTime()
        );
    }
}
