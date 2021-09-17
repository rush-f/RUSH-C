package rush.rush.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    List<UserGroup> findAllByUserId(Long userId);

    List<UserGroup> findAllByGroupId(Long userId);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<UserGroup> findByUserIdAndGroupId(Long userId, Long groupId);

    @Modifying
    @Query("delete from UserGroup userGroup "
        + "where userGroup.user.id = :userId and userGroup.group.id = :groupId")
    void deleteByUserIdAndGroupId(@Param("userId") Long userId, @Param("groupId") Long groupId);

    Long countByGroupId(Long groupId);
}
