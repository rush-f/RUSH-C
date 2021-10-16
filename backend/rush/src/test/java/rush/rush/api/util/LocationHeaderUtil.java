package rush.rush.api.util;

public class LocationHeaderUtil {

    public static Long extractIdFrom(String locationHeader) {
        String[] split = locationHeader.split("/");

        return Long.parseLong(split[split.length - 1]);
    }
}
