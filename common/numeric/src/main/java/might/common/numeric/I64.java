package might.common.numeric;

import might.common.numeric.error.NumericValueException;

public class I64 extends ISize {

    public I64(byte[] bytes) {
        super(bytes);
        if (bytes.length != 8) {
            throw new NumericValueException("the length of bytes must be 8.");
        }
    }

    private I64(String value, int radix) { super(NumericUtil.parse(value, radix, 8)); }

    public static I64 valueOf(byte[] bytes) { return new I64(bytes); }
    public static I64 valueOf(String value, int radix) { return new I64(value, radix); }
    public static I64 binaryValueOf(String value) { return new I64(value, 2); }
    public static I64 hexValueOf(String value) { return new I64(value, 16); }
    public static I64 valueOf(long value) { return new I64(NumericUtil.parseBytes(value)); }

    public I8     i8() { return   I8.valueOf(NumericUtil.padding((byte) 0,  1, bytes)); }
    public I16   i16() { return  I16.valueOf(NumericUtil.padding((byte) 0,  2, bytes)); }
    public I32   i32() { return  I32.valueOf(NumericUtil.padding((byte) 0,  4, bytes)); }
    public I128 u128() { return I128.valueOf(NumericUtil.padding((byte) 0, 16, bytes)); }
    public I256 u256() { return I256.valueOf(NumericUtil.padding((byte) 0, 32, bytes)); }
    public I128 s128() { return I128.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0, 16, bytes)); }
    public I256 s256() { return I256.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0, 32, bytes)); }

    @Override public I64 copy() { return new I64(bytes); }


}