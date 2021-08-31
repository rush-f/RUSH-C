package rush.rush.service.group;

import java.util.List;
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
        List<UserGroup> userGroups = userGroupRepository.findAllByGroupId(groupId);
        UserGroup userGroup = userGroups.stream()
            .filter(userGroup1 -> userGroup1.getUser().getId() == userId)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없거나, "
                + "ID=" + userId + "인 사용자가 ID=" + groupId + "인 그룹을 삭제할 권한이 없습니다."));
        userGroupRepository.deleteById(userGroup.getId());

        if (userGroups.stream().count() <= 1) {
            groupRepository.deleteById(groupId);
        }
    }
}
