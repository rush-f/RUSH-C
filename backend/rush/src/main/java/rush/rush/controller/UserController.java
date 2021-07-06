package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.UserImageResponse;
import rush.rush.dto.UserResponse;
import rush.rush.security.CurrentUser;
import rush.rush.security.user.UserPrincipal;
import rush.rush.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/me/image")
    public ResponseEntity<UserImageResponse> findUserImageUrl(@CurrentUser UserPrincipal userPrincipal) {
        UserImageResponse userImageResponse = userService.findUserImageUrl(userPrincipal);

        return ResponseEntity.ok()
            .body(userImageResponse);
    }

    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> findUser(@CurrentUser UserPrincipal userPrincipal){
            UserResponse userResponse = userService.findUser(userPrincipal);

            return ResponseEntity.ok().
                body(userResponse);
    }
}
