package rush.rush.service.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.repository.UserGroupRepository;

@Service
@RequiredArgsConstructor
public class ChangeImportantService {

    private final UserGroupRepository userGroupRepository;

    @Transactional
    public void changeImportant(Long groupId, User user) {
        UserGroup userGroup = userGroupRepository.findByUserIdAndGroupId(user.getId(), groupId)
            .orElseThrow(() -> new IllegalArgumentException(
                "userId가 " + user.getId() + "이고 groupId가 " + groupId +"인 UserGroup이 존재하지 않습니다."));

        userGroup.changeImportant();
    }
}
