package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.UserImageResponse;
import rush.rush.dto.UserResponse;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class UserController {

    private final UserService userService;

    @GetMapping("/image")
    public ResponseEntity<UserImageResponse> findUserImageUrl(@CurrentUser UserPrincipal userPrincipal) {
        UserImageResponse userImageResponse = userService.findUserImageUrl(userPrincipal.getUser());

        return ResponseEntity.ok()
            .body(userImageResponse);
    }

    @GetMapping
    public ResponseEntity<UserResponse> findUser(@CurrentUser UserPrincipal userPrincipal){
            UserResponse userResponse = userService.findUser(userPrincipal.getUser());

            return ResponseEntity.ok()
                .body(userResponse);
    }

    @GetMapping("/id")
    public ResponseEntity<Long> findId(@CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok()
            .body(userPrincipal.getId());
    }

    @DeleteMapping
    public ResponseEntity<Void> withdrawUser(@CurrentUser UserPrincipal userPrincipal) {
        userService.withdraw(userPrincipal.getUser());

        return ResponseEntity.noContent()
            .build();
    }
}
