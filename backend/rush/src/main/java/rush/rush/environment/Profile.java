package rush.rush.environment;

import java.util.Arrays;
import java.util.List;

public enum Profile {
    LOCAL(8080, "classpath:", Arrays.asList("application.yml", "application-oauth.yml", "application-local.yml")),
    REAL1(8081, "/app/config/", Arrays.asList("application.yml", "application-oauth.yml", "application-real.yml")),
    REAL2(8082, "/app/config/", Arrays.asList("application.yml", "application-oauth.yml", "application-real.yml"));

    private final int port;
    private final String path;
    private List<String> ymlFileNames;

    Profile(int port, String path, List<String> ymlFileNames) {
        this.port = port;
        this.path = path;
        this.ymlFileNames = ymlFileNames;
    }

    public static Profile from(String profileText) {
        return Arrays.stream(values()).filter(profile -> profile.name()
                .equalsIgnoreCase(profileText))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                profileText + "에 해당하는 profile 을 찾을 수 없습니다."));
    }

    public int port() {
        return port;
    }

    public String path() {
        return path;
    }

    public List<String> ymlFileNames() {
        return ymlFileNames;
    }
}
