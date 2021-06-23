package rush.rush;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rush.rush.security.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class RushApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RushApplication.class)
            .run(args);
    }
}
