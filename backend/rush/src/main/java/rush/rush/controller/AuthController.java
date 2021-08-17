package rush.rush.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rush.rush.dto.AuthResponse;
import rush.rush.dto.LoginRequest;
import rush.rush.dto.SignUpRequest;
import rush.rush.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(
        @Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }
}