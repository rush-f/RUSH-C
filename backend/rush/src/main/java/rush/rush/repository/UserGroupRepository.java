package rush.rush.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
