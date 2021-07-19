package rush.rush.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByInvitationCode(String invitationCode);

    @Query("select distinct g from Group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user user "
        + "where user.id = :userId")
    List<Group> findAllByUserId(@Param("userId") Long userId);
}
