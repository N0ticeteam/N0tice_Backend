package N0tice_Project.N0tice_BE.situation.repository;

import N0tice_Project.N0tice_BE.situation.domain.Situation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SituationRepository extends JpaRepository<Situation, Long> {
}
