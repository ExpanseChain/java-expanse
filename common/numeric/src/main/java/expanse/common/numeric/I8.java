package expanse.common.numeric;

import expanse.common.numeric.error.NumericValueException;

import java.util.Arrays;

public class I8 extends ISize {

    public I8(byte[] bytes) {
        super(bytes);
        if (bytes.length != 1) {
            throw new NumericValueException("the length of bytes must be 1.");
        }
    }

    private I8(String value, int radix) { super(NumericUtil.parse(value, radix, 1)); }

    public static I8 valueOf(byte[] bytes) { return new I8(bytes); }
    public static I8 valueOf(String value, int radix) { return new I8(value, radix); }
    public static I8 binaryValueOf(String value) { return new I8(value, 2); }
    public static I8 hexValueOf(String value) { return new I8(value, 16); }
    public static I8 valueOf(int value) { return new I8(new byte[]{(byte) value}); } // 精度损失
    public static I8 valueOf(long value) { return new I8(new byte[]{(byte) value}); } // 精度损失

    public I16   u16() { return  I16.valueOf(NumericUtil.padding((byte) 0,  2, bytes)); }
    public I32   u32() { return  I32.valueOf(NumericUtil.padding((byte) 0,  4, bytes)); }
    public I64   u64() { return  I64.valueOf(NumericUtil.padding((byte) 0,  8, bytes)); }
    public I128 u128() { return I128.valueOf(NumericUtil.padding((byte) 0, 16, bytes)); }
    public I256 u256() { return I256.valueOf(NumericUtil.padding((byte) 0, 32, bytes)); }
    public I16   s16() { return  I16.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0,  2, bytes)); }
    public I32   s32() { return  I32.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0,  4, bytes)); }
    public I64   s64() { return  I64.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0,  8, bytes)); }
    public I128 s128() { return I128.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0, 16, bytes)); }
    public I256 s256() { return I256.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0, 32, bytes)); }

    @Override public I8 copy() { return new I8(bytes); }

    @Override
    public String toString() {
        return "I8{" +
                "bytes=" + Arrays.toString(bytes) +
                '}';
    }
}
