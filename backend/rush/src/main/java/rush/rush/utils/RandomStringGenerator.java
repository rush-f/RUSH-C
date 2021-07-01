package rush.rush.utils;

import java.util.Random;

public class RandomStringGenerator {

    public static String generate(int length) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            stringBuilder.append(generateUpperCase());
        }
        return stringBuilder.toString();
    }

    private static char generateUpperCase() {
        Random r = new Random();
        return (char) (r.nextInt(26) + 'a');
    }
}
