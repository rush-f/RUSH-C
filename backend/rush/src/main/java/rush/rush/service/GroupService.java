package rush.rush.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.dto.CreateGroupRequest;
import rush.rush.dto.GroupResponse;
import rush.rush.dto.GroupSummaryResponse;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.utils.RandomStringGenerator;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    @Transactional
    public Long createGroup(CreateGroupRequest createGroupRequest, User creator) {
        Group savedGroup = saveGroup(createGroupRequest.getName());

        String invitationCode = generateInvitationCode(savedGroup.getId());
        savedGroup.setInvitationCode(invitationCode);

        saveUserGroup(savedGroup, creator);

        return savedGroup.getId();
    }

    @Transactional
    public Long join(String invitationCode, User user) {
        Group group = groupRepository.findByInvitationCode(invitationCode)
                .orElseThrow(() -> new IllegalArgumentException(invitationCode + "는 존재하지 않는 초대코드입니다."));

        validateIfAlreadyJoined(group, user);

        saveUserGroup(group, user);

        return group.getId();
    }

    @Transactional
    public List<GroupSummaryResponse> findAllByUser(User user) {
        List<UserGroup> userGroups = userGroupRepository.findAllByUserId(user.getId());

        return userGroups.stream()
                .map(userGroup -> {
                    Group group = userGroup.getGroup();
                    return new GroupSummaryResponse(group.getId(), group.getName());
                })
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public GroupResponse findOne(Long groupId, User user) {
        UserGroup userGroup = userGroupRepository.findByUserIdAndGroupId(user.getId(), groupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없거나, "
                        + "ID=" + user.getId() + " 사용자가 ID=" + groupId + "인 그룹에 접근할 권한이 없습니다."));
        Group group = userGroup.getGroup();
        return new GroupResponse(group.getId(), group.getName(), group.getInvitationCode(), group.getCreateDate());
    }

    private Group saveGroup(String groupName) {
        Group group = Group.builder()
                .name(groupName)
                .build();
        return groupRepository.save(group);
    }

    private String generateInvitationCode(Long groupId) {
        return RandomStringGenerator.generate(5) + groupId;
    }

    private void validateIfAlreadyJoined(Group group, User user) {
        Optional<UserGroup> userGroup = userGroupRepository
            .findByUserIdAndGroupId(user.getId(), group.getId());

        if (userGroup.isPresent()) {
            throw new IllegalArgumentException("이미 가입된 그룹입니다.");
        }
    }

    private void saveUserGroup(Group group, User user) {
        UserGroup userGroup = UserGroup.builder()
                .group(group)
                .user(user)
                .build();
        userGroupRepository.save(userGroup);
    }
}
