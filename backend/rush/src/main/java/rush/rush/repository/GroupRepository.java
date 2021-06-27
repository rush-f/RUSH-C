package rush.rush.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
