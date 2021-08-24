package rush.rush.environment;

import java.util.Arrays;

public enum Profile {
    LOCAL(8080, "classpath:"),
    REAL1(8081, "/app/config"),
    REAL2(8082, "/app/config");

    private final int port;
    private final String path;

    Profile(int port, String path) {
        this.port = port;
        this.path = path;
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
}
