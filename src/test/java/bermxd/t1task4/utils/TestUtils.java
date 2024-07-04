package bermxd.t1task4.utils;

import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.RandomStringUtils.random;

@UtilityClass
public class TestUtils {
    public static String randomString() {
        return random((int) (Math.random() * 10));
    }

    public static int randomInt() {
        return (int) (Math.random() * 10);
    }
}