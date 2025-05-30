package N0tice_Project.N0tice_BE.user.repository;

import N0tice_Project.N0tice_BE.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
}
