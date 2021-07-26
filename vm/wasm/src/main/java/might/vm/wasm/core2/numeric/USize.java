package might.vm.wasm.core2.numeric;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.model.Dump;

import java.math.BigInteger;

import static might.vm.wasm.util.NumberTransform.*;

/**
 * 数字接口
 */
public interface USize extends Dump {

    int intValue();

    long longValue();

    boolean boolValue();


    byte[] getBytes();

    BigInteger uBidInteger();

    BigInteger sBidInteger();


    String toHexString();

    String toBinaryString();


    int clz();

    int ctz();

    int popcnt();


    /**
     * 将字符数组格式化对应长度字节数组，如果未能提供满足长度的字节，则按照最顶位填充 全0 或 全1
     */
    static byte[] of(byte[] bytes, int size, boolean sign) {
        Assertions.requireTrue(size == 1 || size == 2 || size == 4 || size == 8);

        if (null == bytes) { return new byte[size]; }


        if (bytes.length == size) { return bytes; }

        byte[] bs = new byte[size];

        for (int i = size - 1; 0 <= i; i--) {
            int index = bytes.length - size + i; // 从后往前保存
            if (index < 0) {
                if (sign) {
                    if ((bytes[0] & 0x80) != 0) { // 顶位是1
                        for (int j = 0; j <= i; j++) {
                            bs[j] = -1;
                        }
                    }
                }
                break;
            }
            bs[i] = bytes[index];
        }

        return bs;
    }

    /**
     * 将某进制字符串转换成对应长度字节数组
     */
    static byte[] of(String value, int radix, int size) {
        if (null == value) { return new byte[size]; }

        Assertions.requireTrue(radix == 2 || radix == 16);
        Assertions.requireTrue(size == 1 || size == 2 || size == 4 || size == 8);

        if (radix == 2) {
            Assertions.requireTrue(value.matches("[0|1]{" + (8 * size) + "}"));

            byte[] bytes = new byte[size];
            int length = value.length();
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = parseByteByBinary(value.substring(length - 8 * (size - i), length - 8 * (size - i - 1)));
            }
            return bytes;
        } else {
            value = value.toUpperCase();

            Assertions.requireTrue(value.matches("[0-9A-F]{" + (2 * size) + "}"));

            byte[] bytes = new byte[size];
            int length = value.length();
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = parseByteByHex(value.substring(length - 2 * (size - i), length - 2 * (size - i - 1)));
            }
            return bytes;
        }
    }

    /**
     * 复制数组
     */
    static byte[] copy(byte[] bytes) {
        Assertions.requireNonNull(bytes);

        byte[] bs = new byte[bytes.length];

        System.arraycopy(bytes, 0, bs, 0, bytes.length);

        return bs;
    }

    /**
     * 复制数组
     */
    static byte[] copy(byte[] bytes, int size) {
        Assertions.requireNonNull(bytes);

        byte[] bs = new byte[size];

        if (size <= bytes.length) {
            System.arraycopy(bytes, bytes.length - size, bs, 0, size);
        } else {
            System.arraycopy(bytes, 0, bs, size - bytes.length, bytes.length);
        }

        return bs;
    }

    /**
     * 转化为无符号
     */
    static BigInteger parseUBigInteger(byte[] bytes, boolean bool) {
        return new BigInteger(bool ? 1 : 0, bytes);
    }

    /**
     * 前置0计数
     */
    static int clz(byte[] bytes) {
        String v = toBinaryArray(bytes);
        int count = 0;
        for (int i = 0; i < v.length(); i++) {
            if (v.charAt(i) == '0') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * 后置0计数
     */
    static int ctz(byte[] bytes) {
        String v = toBinaryArray(bytes);
        int count = 0;
        for (int i = v.length() - 1; 0 <= i; i--) {
            if (v.charAt(i) == '0') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * 1计数
     */
    static int popcnt(byte[] bytes) {
        String v = toBinaryArray(bytes);
        int count = 0;
        for (int i = 0; i < v.length(); i++) {
            if (v.charAt(i) != '0') {
                count++;
            }
        }
        return count;
    }

}
