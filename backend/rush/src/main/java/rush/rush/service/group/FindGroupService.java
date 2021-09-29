package rush.rush.service.group;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.dto.GroupResponse;
import rush.rush.dto.GroupSummaryResponse;
import rush.rush.dto.SimpleUserResponse;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FindGroupService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<GroupSummaryResponse> findAllByUser(User user) {
        List<Group> groups = groupRepository.findAllByUserId(user.getId());

        return groups.stream()
            .map(group -> new GroupSummaryResponse(group.getId(), group.getName()))
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<GroupSummaryResponse> findImportantGroupsByUser(User user) {
        return groupRepository.findImportantGroupsByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    public GroupResponse findOne(Long groupId, User user) {
        return groupRepository.findGroupDetail(groupId, user.getId())
            .orElseThrow(() -> new NotAuthorizedOrExistException("해당 그룹이 없거나, "
                + "ID=" + user.getId() + " 사용자가 ID=" + groupId + "인 그룹에 접근할 권한이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<SimpleUserResponse> findMembers(Long groupId, User user) {
        if (!hasJoined(groupId, user.getId())) {
            throw new NotAuthorizedOrExistException("userId=" + user.getId() + "인 사용자가 "
                + "groupId=" + groupId + "인 그룹의 회원목록을 조회할 권한이 없습니다.");
        }
        return userRepository.findAllByGroupId(groupId)
            .stream()
            .map(member -> new SimpleUserResponse(
                member.getId(), member.getNickName(), member.getImageUrl()))
            .collect(Collectors.toUnmodifiableList());
    }

    private boolean hasJoined(Long groupId, Long userId) {
        Optional<UserGroup> userGroup = userGroupRepository
            .findByUserIdAndGroupId(userId, groupId);

        return userGroup.isPresent();
    }
}
