package rush.rush.controller;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final Environment environment;

    @GetMapping("/profile")
    public String profile() {
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        List<String> mainProfiles = Arrays.asList("real1", "real2", "local");

        return profiles.stream()
            .filter(mainProfiles::contains)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(
                "local, real1, real2 중 해당하는 profile이 없습니다.\n"
                    + "현재 profiles : " + String.join(" ", profiles)));
    }
}
