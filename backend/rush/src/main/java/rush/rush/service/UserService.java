package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.User;
import rush.rush.dto.UserImageResponse;
import rush.rush.dto.UserResponse;
import rush.rush.exception.NotUserExistsException;
import rush.rush.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserImageResponse findUserImageUrl(User user) {
        User foundUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new NotUserExistsException(
                "존재하지 않는 회원입니다."));
        return new UserImageResponse(foundUser.getId(), foundUser.getImageUrl());
    }

    @Transactional
    public UserResponse findUser(User user) {
        User foundUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new NotUserExistsException(
                "존재하지 않는 회원입니다."));
        return new UserResponse(
            foundUser.getId(),
            foundUser.getImageUrl(),
            foundUser.getNickName(),
            foundUser.getEmail(),
            foundUser.getVisitDate());
    }

    @Transactional
    public void withdraw(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new NotUserExistsException("존재하지 않는 회원입니다.");
        }
        userRepository.deleteById(user.getId());
    }
}
