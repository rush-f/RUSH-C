package rush.rush.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<Group> findByInvitationCode(String invitationCode);

    @Query("select distinct g from Group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user user "
        + "where user.id = :userId and g.id = :groupId")
    Optional<Group> findByGroupIdAndUserId(@Param("groupId") Long groupId, @Param("userId") Long userId);

    @Query("select distinct g from Group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user user "
        + "where user.id = :userId")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Group> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("update Group g set g.name = :newGroupName where g.id = :groupId")
    void editGroupName(@Param("groupId") Long groupId, @Param("newGroupName") String newGroupName);
}
