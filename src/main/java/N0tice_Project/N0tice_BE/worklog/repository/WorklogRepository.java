package N0tice_Project.N0tice_BE.worklog.repository;

import N0tice_Project.N0tice_BE.worklog.domain.Worklog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorklogRepository extends JpaRepository <Worklog, Long> {
}
