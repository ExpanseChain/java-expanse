package might.common.numeric;

import might.common.numeric.error.NumericValueException;

public class I256 extends ISize {

    public I256(byte[] bytes) {
        super(bytes);
        if (bytes.length != 32) {
            throw new NumericValueException("the length of bytes must be 32.");
        }
    }

    private I256(String value, int radix) { super(NumericUtil.parse(value, radix, 32)); }

    public static I256 valueOf(byte[] bytes) { return new I256(bytes); }
    public static I256 valueOf(String value, int radix) { return new I256(value, radix); }
    public static I256 binaryValueOf(String value) { return new I256(value, 2); }
    public static I256 hexValueOf(String value) { return new I256(value, 16); }

    public I8     i8() { return   I8.valueOf(NumericUtil.padding((byte) 0,  1, bytes)); }
    public I16   i16() { return  I16.valueOf(NumericUtil.padding((byte) 0,  2, bytes)); }
    public I32   i32() { return  I32.valueOf(NumericUtil.padding((byte) 0,  4, bytes)); }
    public I64   i64() { return  I64.valueOf(NumericUtil.padding((byte) 0,  8, bytes)); }
    public I128 i128() { return I128.valueOf(NumericUtil.padding((byte) 0, 16, bytes)); }

    @Override public I256 copy() { return new I256(bytes); }


}
