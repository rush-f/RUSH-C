package rush.rush.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
