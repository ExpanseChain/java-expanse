package might.common.numeric;

import might.common.numeric.error.NumericException;
import might.common.numeric.error.NumericNullException;
import might.common.numeric.error.NumericValueException;

import java.util.Arrays;

public class NumericUtil {

    /**
     * 检查字符是否符合条件
     * @param value 字符串值
     * @param radix 进制
     * @param size 长度检查 -1 表示不检查
     */
    static byte[] parse(String value, int radix, int size) {
        if (null == value) {
            throw new NumericNullException("value can not be null");
        }

        // 检查是否包含错误字符
        String regex = getCharsRegex(radix);
        if (!value.matches(regex)) {
            throw new NumericValueException(String.format("wrong number character in %s for radix %d.", value, radix));
        }

        // 检查长度是否符合字节标准
        checkLength(value.length(), radix);

        byte[] bytes;
        switch (radix) {
            case 2:
                bytes = new byte[value.length() / 8];
                if (0 < size && size != bytes.length) {
                    throw new NumericValueException("the length of value must be " + (8 * size));
                }
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte) Integer.parseInt(value.substring(8 * i, 8 * i + 8), 2);
                }
                break;
            case 16:
                bytes = new byte[value.length() / 2];
                if (0 < size && size != bytes.length) {
                    throw new NumericValueException("the length of value must be " + (2 * size));
                }
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte) Integer.parseInt(value.substring(2 * i, 2 * i + 2), 16);
                }
                break;
            default:
                throw new NumericValueException("radix is not support: " + radix);
        }

        return bytes;
    }

    private static void checkLength(int length, int radix) {
        switch (radix) {
            case  2: if (length % 8 == 0) { return; } break;
            case 16: if (length % 2 == 0) { return; } break;
        }
        throw new NumericValueException(String.format(
                "The length of radix(%d) number can not be %d. (must be a multiple of %d)",
                radix, length, 2 == radix ? 8 : 2));
    }

    private static String getCharsRegex(int radix) {
        switch (radix) {
            case  2: return "[0-1]+";
            case 16: return "[0-9A-Fa-f]+";
        }
        throw new NumericValueException("radix is not support: " + radix);
    }

    static String hexString(byte value) {
        return new String(new char[]{hexChar((value & 0xF0) >> 4), hexChar(value & 0xF)}) ;
    }

    private static char hexChar(int value) {
        switch (value) {
            case  0: return '0';
            case  1: return '1';
            case  2: return '2';
            case  3: return '3';
            case  4: return '4';
            case  5: return '5';
            case  6: return '6';
            case  7: return '7';
            case  8: return '8';
            case  9: return '9';
            case 10: return 'A';
            case 11: return 'B';
            case 12: return 'C';
            case 13: return 'D';
            case 14: return 'E';
            case 15: return 'F';
        }
        throw new NumericException(String.format("what a value(%d)?", value));
    }

    public static String binaryString(byte value) {
        String v = Integer.toBinaryString(value);
        switch (v.length()) {
            case 1: return "0000000" + v;
            case 2: return "000000" + v;
            case 3: return "00000" + v;
            case 4: return "0000" + v;
            case 5: return "000" + v;
            case 6: return "00" + v;
            case 7: return "0" + v;
            case 8: return v;
            default: return v.substring(v.length() - 8);
        }
    }

    static String toHexArray(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) { sb.append(hexString(b)); }
        return sb.toString();
    }

    static String toBinaryArray(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) { sb.append(binaryString(b)); }
        return sb.toString();
    }

    private static int clz(byte b) {
        if (b == 0) { return 8; }
        if ((b & 0b10000000) != 0) return 0;
        if ((b & 0b01000000) != 0) return 1;
        if ((b & 0b00100000) != 0) return 2;
        if ((b & 0b00010000) != 0) return 3;
        if ((b & 0b00001000) != 0) return 4;
        if ((b & 0b00000100) != 0) return 5;
        if ((b & 0b00000010) != 0) return 6;
        if ((b & 0b00000001) != 0) return 7;
        return 8;
    }

    private static int ctz(byte b) {
        if (b == 0) { return 8; }
        if ((b & 0b00000001) != 0) return 0;
        if ((b & 0b00000010) != 0) return 1;
        if ((b & 0b00000100) != 0) return 2;
        if ((b & 0b00001000) != 0) return 3;
        if ((b & 0b00010000) != 0) return 4;
        if ((b & 0b00100000) != 0) return 5;
        if ((b & 0b01000000) != 0) return 6;
        if ((b & 0b10000000) != 0) return 7;
        return 8;
    }

    private static int popcnt(byte b) {
        if (b == 0) { return 0; }
        if (b == -1) { return 8; }
        int count = 0;
        if ((b & 0b00000001) != 0) count++;
        if ((b & 0b00000010) != 0) count++;
        if ((b & 0b00000100) != 0) count++;
        if ((b & 0b00001000) != 0) count++;
        if ((b & 0b00010000) != 0) count++;
        if ((b & 0b00100000) != 0) count++;
        if ((b & 0b01000000) != 0) count++;
        if ((b & 0b10000000) != 0) count++;
        return count;
    }

    /**
     * 前置0计数
     */
    static int clz(byte[] bytes) {
        int count = 0;
        for (byte b : bytes) {
            if (b == 0) {
                count += 8;
            } else {
                count += clz(b);
                break;
            }
        }
        return count;
    }

    /**
     * 后置0计数
     */
    static int ctz(byte[] bytes) {
        int count = 0;
        for (int i = bytes.length - 1; 0 <= i; i--) {
            if (bytes[i] == 0) {
                count += 8;
            } else {
                count += ctz(bytes[i]);
                break;
            }
        }
        return count;
    }

    /**
     * 1计数 字符数组不会长度大到int溢出吧？
     */
    static int popcnt(byte[] bytes) {
        int count = 0;
        for (byte b : bytes) {
            count += popcnt(b);
        }
        return count;
    }

    /**
     * 32位有符号数字转化为字节数组
     */
    public static byte[] parseBytes(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value >> 24);
        bytes[1] = (byte) (value >> 16);
        bytes[2] = (byte) (value >>  8);
        bytes[3] = (byte) (value);
        return bytes;
    }

    /**
     * 64位有符号数字转化为字节数组
     */
    public static byte[] parseBytes(long value) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (value >> 56);
        bytes[1] = (byte) (value >> 48);
        bytes[2] = (byte) (value >> 40);
        bytes[3] = (byte) (value >> 32);
        bytes[4] = (byte) (value >> 24);
        bytes[5] = (byte) (value >> 16);
        bytes[6] = (byte) (value >>  8);
        bytes[7] = (byte) (value);
        return bytes;
    }

    static byte[] padding(byte value, int size, byte[] data) {
        byte[] bytes = new byte[size];
        if (size > data.length) {
            System.arraycopy(data, 0, bytes, size - data.length, data.length);
            Arrays.fill(bytes, 0, size - data.length, value);
        } else {
            System.arraycopy(data, data.length - size, bytes, 0, size);
        }
        return bytes;
    }

}
