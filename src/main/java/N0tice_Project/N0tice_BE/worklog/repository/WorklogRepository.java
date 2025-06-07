package N0tice_Project.N0tice_BE.worklog.repository;

import N0tice_Project.N0tice_BE.user.domain.User;
import N0tice_Project.N0tice_BE.worklog.domain.Worklog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorklogRepository extends JpaRepository <Worklog, Long> {

    Optional<Worklog> findByUserIdUserIdAndLogDate(Long userId, LocalDate logDate);

    List<Worklog> findAllByUserIdUserIdAndLogDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    boolean existsByUserIdAndLogDate(User user, LocalDate logDate);

}
