package might.common.util;

public class HexUtil {

    public static String toHex(byte[] bytes) {
        if (null == bytes) { return ""; }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toHexString(b));
        }
        return sb.toString();
    }

}
