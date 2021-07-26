package might.common.numeric;

import might.common.numeric.error.NumericValueException;

import java.util.Arrays;

public class I32 extends ISize {

    public I32(byte[] bytes) {
        super(bytes);
        if (bytes.length != 4) {
            throw new NumericValueException("the length of bytes must be 4.");
        }
    }

    private I32(String value, int radix) {
        super(NumericUtil.parse(value, radix, 4));
    }

    public static I32 valueOf(byte[] bytes) { return new I32(bytes); }
    public static I32 valueOf(String value, int radix) { return new I32(value, radix); }
    public static I32 binaryValueOf(String value) { return new I32(value, 2); }
    public static I32 hexValueOf(String value) { return new I32(value, 16); }
    public static I32 valueOf(int value) { return new I32(NumericUtil.parseBytes(value)); }
    public static I32 valueOf(long value) { return new I32(new byte[]{ ((byte) ((value & 0xFF000000) >> 24)), ((byte) ((value & 0xFF0000) >> 16)), ((byte) ((value & 0xFF00) >> 8)), (byte) value }); } // 精度损失

    public I8     i8() { return   I8.valueOf(NumericUtil.padding((byte) 0,  1, bytes)); }
    public I16   i16() { return  I16.valueOf(NumericUtil.padding((byte) 0,  2, bytes)); }
    public I64   u64() { return  I64.valueOf(NumericUtil.padding((byte) 0,  8, bytes)); }
    public I128 u128() { return I128.valueOf(NumericUtil.padding((byte) 0, 16, bytes)); }
    public I256 u256() { return I256.valueOf(NumericUtil.padding((byte) 0, 32, bytes)); }
    public I64   s64() { return  I64.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0,  8, bytes)); }
    public I128 s128() { return I128.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0, 16, bytes)); }
    public I256 s256() { return I256.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0, 32, bytes)); }

    @Override public I32 copy() { return new I32(bytes); }

    public I32 add(I32 b) { return I32.valueOf(signed().intValue() + b.signed().intValue()); }
    public I32 sub(I32 b) { return I32.valueOf(signed().intValue() - b.signed().intValue()); }
    public I32 mul(I32 b) { return I32.valueOf(signed().intValue() * b.signed().intValue()); }

    @Override
    public String toString() {
        return "I32{" +
                "bytes=" + Arrays.toString(bytes) +
                '}';
    }

}
