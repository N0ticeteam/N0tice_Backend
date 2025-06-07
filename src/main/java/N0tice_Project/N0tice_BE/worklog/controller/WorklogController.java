package N0tice_Project.N0tice_BE.worklog.controller;

import N0tice_Project.N0tice_BE.worklog.dto.MonthlyWorklogResponse;
import N0tice_Project.N0tice_BE.worklog.dto.WorklogRequest;
import N0tice_Project.N0tice_BE.worklog.dto.WorklogResponse;
import N0tice_Project.N0tice_BE.worklog.service.WorklogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Worklog API", description = "작업일지 작성·조회 관련 API")
@RestController
@RequestMapping("/api/work-logs")
public class WorklogController {

    private final WorklogService worklogService;

    @Autowired
    public WorklogController(WorklogService worklogService) {
        this.worklogService = worklogService;
    }

    //작업일지 등록
    @Operation(
            summary = "작업일지 등록",
            description = "특정 날짜에 대한 새로운 작업일지를 작성합니다.")
    @PostMapping
    public WorklogResponse createWorklog(@RequestBody WorklogRequest request) {
        return worklogService.createWorklog(request);
    }

    //특정 날짜 일지 조회
    @Operation(
            summary = "특정 날짜 작업일지 조회",
            description = "특정 날짜에 기록한 작업일지를 조회합니다.")
    @GetMapping
    public WorklogResponse getWorklogByDate(
            @RequestParam("userId") Long userId,
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return worklogService.getWorklogByDate(userId, date);
    }

    //월간 일지 조회 (달력용)
    @Operation(
            summary = "월간 작업일지 조회",
            description = "해당 월에 기록한 작업일지를 조회합니다.")
    @GetMapping("/monthly")
    public List<MonthlyWorklogResponse> getMonthlyWorklogs(
            @RequestParam("userId") Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        return worklogService.getMonthlyWorklogs(userId, year, month);
    }
}
