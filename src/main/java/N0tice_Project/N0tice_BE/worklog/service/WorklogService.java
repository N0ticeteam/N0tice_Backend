package N0tice_Project.N0tice_BE.worklog.service;

import N0tice_Project.N0tice_BE.global.exception.GeneralException;
import N0tice_Project.N0tice_BE.global.status.ErrorStatus;
import N0tice_Project.N0tice_BE.user.domain.User;
import N0tice_Project.N0tice_BE.user.repository.UserRepository;
import N0tice_Project.N0tice_BE.worklog.domain.Worklog;
import N0tice_Project.N0tice_BE.worklog.dto.MonthlyWorklogResponse;
import N0tice_Project.N0tice_BE.worklog.dto.WorklogRequest;
import N0tice_Project.N0tice_BE.worklog.dto.WorklogResponse;
import N0tice_Project.N0tice_BE.worklog.repository.WorklogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorklogService {

    private final WorklogRepository worklogRepository;
    private final UserRepository userRepository;

    public WorklogResponse createWorklog(WorklogRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다. id=" + request.getUserId()));

        boolean exists = worklogRepository.existsByUserIdAndLogDate(user, request.getLogDate());

        if (exists) {
            throw new GeneralException(ErrorStatus._DUPLICATE_RESOURCE);
        }

        Worklog worklog = Worklog.builder()
                .userId(user)
                .logDate(request.getLogDate())
                .title(request.getTitle())
                .content(request.getContent())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .managerName(request.getManagerName())
                .agentName(request.getAgentName())
                .accidentRelatedNotes(
                        request.getAccidentRelatedNotes() != null ? request.getAccidentRelatedNotes() : ""
                )
                .build();

        Worklog saved = worklogRepository.save(worklog);

        return WorklogResponse.of(saved);
    }

    //특정 날짜(userId, logDate)에 해당하는 일지 조회
    public WorklogResponse getWorklogByDate(Long userId, LocalDate logDate) {
        Worklog workLog = worklogRepository.findByUserIdUserIdAndLogDate(userId, logDate)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "해당 날짜의 작업일지를 찾을 수 없습니다. userId=" + userId + ", logDate=" + logDate));

        return WorklogResponse.of(workLog);
    }

    //월간(연도+월) 일지 목록 조회 (달력용)
    public List<MonthlyWorklogResponse> getMonthlyWorklogs(Long userId, int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atEndOfMonth();

        List<Worklog> logs = worklogRepository.findAllByUserIdUserIdAndLogDateBetween(userId, startDate, endDate);

        return logs.stream()
                .map(MonthlyWorklogResponse::from)
                .collect(Collectors.toList());
    }
}
