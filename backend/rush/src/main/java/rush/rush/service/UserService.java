package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.User;
import rush.rush.dto.UserImageResponse;
import rush.rush.dto.UserResponse;
import rush.rush.repository.UserRepository;
import rush.rush.security.BadRequestException;
import rush.rush.security.user.UserPrincipal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserImageResponse findUserImageUrl(User user) {
        User foundUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new IllegalArgumentException(
                "userId가 " + user.getId() + "인 user를 찾지 못했습니다."));
        return new UserImageResponse(foundUser.getId(), foundUser.getImageUrl());
    }

    @Transactional
    public UserResponse findUser(User user) {
        User foundUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new IllegalArgumentException(
                "userId가 " + user.getId() + "인 user를 찾지 못했습니다."));
        return new UserResponse(
            foundUser.getId(),
            foundUser.getImageUrl(),
            foundUser.getNickName(),
            foundUser.getEmail(),
            foundUser.getVisitDate());
    }

    public void withdraw(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new BadRequestException("존재하지 않는 회원입니다.");
        }
        userRepository.deleteById(user.getId());
    }
}
