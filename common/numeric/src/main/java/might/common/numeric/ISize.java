package might.common.numeric;

import might.common.numeric.error.NumericNullException;
import might.common.numeric.error.NumericValueException;

import java.math.BigInteger;
import java.util.Arrays;

import static might.common.numeric.NumericUtil.*;

/**
 * 整型数字 以字节表示的数字
 * 具体解释成无符号还是有符号看具体使用
 * 大端表示
 * 不可变对象
 */
public class ISize {

    private static final int MAX_LENGTH = 1024 * 1024; // 支持的最大字节数 这个数字也太大了

    private final byte[] bytes;

    public ISize(byte[] bytes) {
        if (null == bytes) {
            throw new NumericNullException("bytes can not be null");
        }
        if (bytes.length > MAX_LENGTH) {
            throw new NumericValueException("the length of bytes is too large.");
        }

        this.bytes = new byte[bytes.length];
        System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
    }

    private ISize(String value, int radix) {
        NumericUtil.checkChar(value, radix);

        switch (radix) {
            case 2:
                this.bytes = new byte[value.length() / 8];
                for (int i = 0; i < this.bytes.length; i++) {
                    this.bytes[i] = (byte) Integer.parseInt(value.substring(8 * i, 8 * i + 8), 2);
                }
                break;
            case 16:
                this.bytes = new byte[value.length() / 2];
                for (int i = 0; i < this.bytes.length; i++) {
                    this.bytes[i] = (byte) Integer.parseInt(value.substring(2 * i, 2 * i + 2), 16);
                }
                break;
            default:
                throw new NumericValueException("radix is not support: " + radix);
        }
    }

    public static ISize valueOf(byte[] bytes) { return new ISize(bytes); }
    public static ISize parseByBinary(String value) { return new ISize(value, 2); }
    public static ISize parseByHex(String value) { return new ISize(value, 16); }
    public static ISize valueOf(int value) { return new ISize(NumericUtil.parseBytes(value)); }
    public static ISize valueOf(long value) { return new ISize(NumericUtil.parseBytes(value)); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ISize iSize = (ISize) o;
        return Arrays.equals(bytes, iSize.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return "ISize{" +
                "bytes=" + Arrays.toString(bytes) +
                '}';
    }

    /**
     * 字节数
     */
    public int size() { return this.bytes.length; }

    /**
     * 获取字节数组 复制一份
     */
    public byte[] bytes() {
        byte[] bytes = new byte[this.bytes.length];
        System.arraycopy(this.bytes, 0, bytes, 0, bytes.length);
        return bytes;
    }

    /**
     * 复制一份数字 不可变类型 clone一份
     */
    public ISize value() {
        return new ISize(this.bytes);
    }

    /**
     * 转化为布尔值
     */
    public boolean booleanValue() {
        for (byte b : bytes) {
            if (b != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 无符号数
     */
    public BigInteger unsigned() { return new BigInteger(1, bytes); }

    /**
     * 有符号数
     */
    public BigInteger signed() { return new BigInteger(bytes); }

    /**
     * 转换成16进制
     */
    public String toHexString() { return toHexArray(bytes); }

    /**
     * 转换2进制
     */
    public String toBinaryString() { return toBinaryArray(bytes); }

    /**
     * 前置0统计
     */
    public int clz() { return NumericUtil.clz(bytes); }

    /**
     * 后置0统计
     */
    public int ctz() { return NumericUtil.ctz(bytes); }

    /**
     * 1统计
     */
    public int popcnt() { return NumericUtil.popcnt(bytes); }

}
