package rush.rush.domain;

import java.util.Arrays;
import rush.rush.exception.WrongMapTypeException;

public enum MapType {
    PUBLIC("public"), PRIVATE("private"), GROUPED("grouped");

    private String name;

    MapType(String name) {
        this.name = name;
    }

    public static MapType from(String mapType) {
        return Arrays.stream(values())
            .filter(value -> value.name.equalsIgnoreCase(mapType))
            .findFirst()
            .orElseThrow(() -> new WrongMapTypeException(
                mapType + "에 해당하는 MapType이 존재하지 않습니다."));
    }
}
