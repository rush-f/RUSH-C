package rush.rush.service.group;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.exception.AlreadySignedUpException;
import rush.rush.exception.NotExistsException;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;

@Service
@RequiredArgsConstructor
public class JoinGroupService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    @Transactional
    public Long join(String invitationCode, User user) {
        Group group = groupRepository.findByInvitationCode(invitationCode)
            .orElseThrow(() -> new NotExistsException(invitationCode + "는 존재하지 않는 초대코드입니다."));

        if (hasJoined(group.getId(), user.getId())) {
            throw new AlreadySignedUpException("이미 가입된 그룹입니다.");
        }
        saveUserGroup(group, user);

        return group.getId();
    }

    private boolean hasJoined(Long groupId, Long userId) {
        Optional<UserGroup> userGroup = userGroupRepository
            .findByUserIdAndGroupId(userId, groupId);

        return userGroup.isPresent();
    }

    private void saveUserGroup(Group group, User user) {
        UserGroup userGroup = UserGroup.builder()
            .group(group)
            .user(user)
            .build();
        userGroupRepository.save(userGroup);
    }
}
