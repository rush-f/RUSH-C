package rush.rush;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rush.rush.security.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class RushApplication {

    private static final String REAL_SERVER_OPTION = "REAL";
    private static final String REAL_SERVER_PROPERTY_LOCATION = "/app/config/";
    private static final String LOCAL_SERVER_PROPERTY_LOCATION = "classpath:";

    public static void main(String[] args) {
        final String PROPERTY_LOCATIONS = makeSApplicationLocations(args);

        new SpringApplicationBuilder(RushApplication.class)
            .properties(PROPERTY_LOCATIONS)
            .run(args);
    }

    private static String makeSApplicationLocations(String[] mainMethodArguments) {
        if (mainMethodArguments.length > 0
                && mainMethodArguments[0].equalsIgnoreCase(REAL_SERVER_OPTION)) {
            return "spring.config.location="
                + REAL_SERVER_PROPERTY_LOCATION + "application.yml,"
                + REAL_SERVER_PROPERTY_LOCATION + "application-oauth.yml,"
                + REAL_SERVER_PROPERTY_LOCATION + "application-real.yml";
        }
        return "spring.config.location="
            + LOCAL_SERVER_PROPERTY_LOCATION + "application.yml,"
            + LOCAL_SERVER_PROPERTY_LOCATION + "application-oauth.yml,"
            + LOCAL_SERVER_PROPERTY_LOCATION + "application-local.yml";
    }
}
