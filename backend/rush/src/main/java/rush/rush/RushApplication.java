package rush.rush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rush.rush.security.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class RushApplication {

    public static void main(String[] args) {
        SpringApplication.run(RushApplication.class, args);
    }
}
