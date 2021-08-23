package rush.rush.service.group;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;

@Service
@RequiredArgsConstructor
public class EditGroupNameService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    @Transactional
    public void editGroupName(Long groupId, String newGroupName, User user) {
        if (!hasJoined(groupId, user.getId())) {
            throw new IllegalArgumentException("groupId=" + groupId + "인 그룹이 없거나, "
                + "userId=" + user.getId() + "인 사용자에게 "
                + "groupId=" + groupId + "인 그룹의 이름을 수정할 권한이 없습니다.");
        }
        groupRepository.editGroupName(groupId, newGroupName);
    }

    // Todo: 인터셉터나 스프링시큐리티로 옮길것
    private boolean hasJoined(Long groupId, Long userId) {
        Optional<UserGroup> userGroup = userGroupRepository
            .findByUserIdAndGroupId(userId, groupId);

        return userGroup.isPresent();
    }
}
