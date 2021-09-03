package rush.rush.service.group;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.UserGroup;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;

@Service
@RequiredArgsConstructor
public class DeleteMemberService {

    private final GroupRepository groupRepository;

    private final UserGroupRepository userGroupRepository;

    @Transactional
    public void deleteMember(Long groupId, Long userId) {
        if (!hasJoined(groupId, userId)) {
            throw new IllegalArgumentException("groupId=" + groupId + "인 그룹이 없거나, "
                + "userId=" + userId + "인 사용자에게 "
                + "groupId=" + groupId + "인 그룹을 탈퇴할 권한이 없습니다.");
        }
        deleteUserGroup(groupId, userId);
        deleteGroupIfMemberIsNotExist(groupId);
    }

    private void deleteUserGroup(Long groupId, Long userId) {
        userGroupRepository.deleteByUserIdAndGroupId(userId, groupId);
    }

    private void deleteGroupIfMemberIsNotExist(Long groupId) {
        Long userCount = userGroupRepository.countByGroupId(groupId);
        if (userCount <= 0) {
            groupRepository.deleteById(groupId);
        }
    }

    // Todo: 인터셉터나 스프링시큐리티로 옮길것
    private boolean hasJoined(Long groupId, Long userId) {
        Optional<UserGroup> userGroup = userGroupRepository
            .findByUserIdAndGroupId(userId, groupId);

        return userGroup.isPresent();
    }
}
