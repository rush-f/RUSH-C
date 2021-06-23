package rush.rush;

import java.util.Arrays;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rush.rush.security.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class RushApplication {

    public static void main(String[] args) {
        String locations;

        if (args.length > 0 && args[0].equalsIgnoreCase("real")) {
            locations = "spring.config.location="
                + "/app/config/application.yml,"
                + "/app/config/application-oauth.yml,"
                + "/app/config/application-real.yml";
        } else {
            locations = "spring.config.location="
                + "classpath:application.yml,"
                + "classpath:application-oauth.yml,"
                + "classpath:application-local.yml";
        }
        new SpringApplicationBuilder(RushApplication.class)
            .properties(locations)
            .run(args);
    }
}
