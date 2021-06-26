package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.User;
import rush.rush.dto.UserImageResponse;
import rush.rush.repository.UserRepository;
import rush.rush.security.user.UserPrincipal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserImageResponse findUserImageUrl(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
            .orElseThrow(() -> new IllegalArgumentException(
                "userId가 " + userPrincipal.getId() + "인 user를 찾지 못했습니다."));
        return new UserImageResponse(user.getId(), user.getImageUrl());
    }
}
