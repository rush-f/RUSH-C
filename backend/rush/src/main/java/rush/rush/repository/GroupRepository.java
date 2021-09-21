package rush.rush.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.Group;
import rush.rush.dto.GroupResponse;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByInvitationCode(String invitationCode);

    @Query("select distinct new rush.rush.dto.GroupResponse(g.id, g.name, g.invitationCode, usergroup.important, g.createDate) "
        + "from Group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user user "
        + "where user.id = :userId and g.id = :groupId")
    Optional<GroupResponse> findGroupDetail(@Param("groupId") Long groupId, @Param("userId") Long userId);

    @Query("select distinct g from Group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user user "
        + "where user.id = :userId")
    List<Group> findAllByUserId(@Param("userId") Long userId);

    @Query("select distinct g from Group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user user "
        + "where user.id = :userId "
        + "and usergroup.important = true")
    List<Group> findImportantGroupsByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("update Group g set g.name = :newGroupName where g.id = :groupId")
    void editGroupName(@Param("groupId") Long groupId, @Param("newGroupName") String newGroupName);
}
