package rush.rush;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rush.rush.security.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class RushApplication {

    private static final String PROFILE_LOCAL = "LOCAL";
    private static final String PROFILE_REAL1 = "REAL1";
    private static final String PROFILE_REAL2 = "REAL2";

    private static final String REAL_PATH = "/app/config/";
    private static final String LOCAL_PATH = "classpath:";

    public static void main(String[] args) {
        String profile = decideActiveProfile(args);
        int port = decidePort(profile);

        String locationConfig = location(profile);
        String portConfig = port(port);

        new SpringApplicationBuilder(RushApplication.class)
            .properties(locationConfig)
            .properties(portConfig)
            .run(args);
    }

    private static String decideActiveProfile(String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase(PROFILE_LOCAL)) {
            return PROFILE_LOCAL;
        }
        if (args[0].equalsIgnoreCase(PROFILE_REAL1) || args[0].equalsIgnoreCase(PROFILE_REAL2)) {
            return args[0].toUpperCase();
        }
        throw new IllegalArgumentException("파라미터가 잘못되었음");
    }

    private static int decidePort(String profile) {
        if (PROFILE_REAL1.equals(profile)) {
            return 8081;
        }
        if (PROFILE_REAL2.equals(profile)) {
            return 8082;
        }
        if (PROFILE_LOCAL.equals(profile)) {
            return 8080;
        }
        throw new IllegalArgumentException("파라미터 profile=" + profile + "가 올바르지 않습니다.");
    }

    private static String location(String profile) {
        if (PROFILE_REAL1.equals(profile) || PROFILE_REAL2.equals(profile)) {    // 실서버
            return makeLocations(REAL_PATH);
        }
        if (PROFILE_LOCAL.equals(profile)) {            // 로컬
            return makeLocations(LOCAL_PATH);
        }
        throw new IllegalArgumentException("profile(" + profile + ")이 로컬 또는 실서버에 매치되지 않음");
    }

    private static String makeLocations(String propertyFilePath) {
        return "spring.config.location="
            + propertyFilePath + "application.yml,"
            + propertyFilePath + "application-oauth.yml,"
            + propertyFilePath + "application-local.yml";
    }

    private static String port(int port) {
        return "server.port=" + port;
    }
}
