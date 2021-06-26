package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.User;
import rush.rush.dto.AuthResponse;
import rush.rush.dto.LoginRequest;
import rush.rush.dto.SignUpRequest;
import rush.rush.repository.UserRepository;
import rush.rush.security.BadRequestException;
import rush.rush.security.TokenProvider;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // Todo: 컨트롤러 - 리포지토리 의존 제거할것 (서비스에 맡길것)
    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("이미 사용중인 이메일입니다.");
        }
        User user = User.builder()
            .nickName(signUpRequest.getNickName())
            .email(signUpRequest.getEmail())
            .provider(AuthProvider.local)
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
//            .invitationCode(HashUtil.hash(signUpRequest.getEmail()))
            .build();

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .build();
    }

}