package rush.rush.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("select user from User user "
        + "inner join user.userGroups usergroups "
        + "inner join usergroups.group g "
        + "where g.id = :groupId")
    List<User> findAllByGroupId(@Param("groupId") Long groupId);
}
