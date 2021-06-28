package rush.rush.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.UserGroup;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    List<UserGroup> findAllByUserId(Long userId);

    Optional<UserGroup> findByUserIdAndGroupId(Long userId, Long groupId);
}
