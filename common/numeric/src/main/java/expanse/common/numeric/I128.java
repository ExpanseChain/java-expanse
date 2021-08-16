package expanse.common.numeric;

import expanse.common.numeric.error.NumericValueException;

import java.util.Arrays;

public class I128 extends ISize {

    public I128(byte[] bytes) {
        super(bytes);
        if (bytes.length != 16) {
            throw new NumericValueException("the length of bytes must be 16.");
        }
    }

    private I128(String value, int radix) { super(NumericUtil.parse(value, radix, 16)); }

    public static I128 valueOf(byte[] bytes) { return new I128(bytes); }
    public static I128 valueOf(String value, int radix) { return new I128(value, radix); }
    public static I128 binaryValueOf(String value) { return new I128(value, 2); }
    public static I128 hexValueOf(String value) { return new I128(value, 16); }

    public I8     i8() { return   I8.valueOf(NumericUtil.padding((byte) 0,  1, bytes)); }
    public I16   i16() { return  I16.valueOf(NumericUtil.padding((byte) 0,  2, bytes)); }
    public I32   i32() { return  I32.valueOf(NumericUtil.padding((byte) 0,  4, bytes)); }
    public I64   i64() { return  I64.valueOf(NumericUtil.padding((byte) 0,  8, bytes)); }
    public I256 u256() { return I256.valueOf(NumericUtil.padding((byte) 0, 32, bytes)); }
    public I256 s256() { return I256.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0, 32, bytes)); }

    @Override public I128 copy() { return new I128(bytes); }

    @Override
    public String toString() {
        return "I128{" +
                "bytes=" + Arrays.toString(bytes) +
                '}';
    }

}
