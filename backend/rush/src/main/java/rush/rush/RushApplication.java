package rush.rush;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rush.rush.environment.Profile;
import rush.rush.security.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class RushApplication {

    public static void main(String[] args) {
        Profile profile = decideProfile(args);

        new SpringApplicationBuilder(RushApplication.class)
            .properties(location(profile.path()))
            .properties(port(profile.port()))
            .run(args);
    }

    private static Profile decideProfile(String[] args) {
        if (args.length == 0) {
            return Profile.from("LOCAL");
        }
        return Profile.from(args[0]);
    }

    private static String location(String path) {
        return "spring.config.location="
            + path + "application.yml,"
            + path + "application-oauth.yml,"
            + path + "application-local.yml";
    }

    private static String port(int port) {
        return "server.port=" + port;
    }
}
