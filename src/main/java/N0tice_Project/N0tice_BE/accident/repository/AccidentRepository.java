package N0tice_Project.N0tice_BE.accident.repository;

import N0tice_Project.N0tice_BE.accident.domain.Accident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccidentRepository extends JpaRepository<Accident,Long> {
}
