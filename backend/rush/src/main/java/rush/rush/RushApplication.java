package rush.rush;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rush.rush.security.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class RushApplication {

    private static final String APPLICATION_LOCATIONS = "spring.config.location="
        + "classpath:application.yml,"
        + "classpath:application-oauth.yml,"
        + "classpath:application-local.yml,"
        + "optional:/app/config/application-real.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(RushApplication.class)
            .properties(APPLICATION_LOCATIONS)
            .run(args);
    }
}
