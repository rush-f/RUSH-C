package rush.rush.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final Environment environment;

    @GetMapping("/port")
    public String port() {
        return environment.getProperty("server.port");
    }
}
