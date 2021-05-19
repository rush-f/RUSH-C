package rush.rush.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class HashUtil {

    public static String hash(String email) {
        return DigestUtils.sha256Hex(email);
    }
}
