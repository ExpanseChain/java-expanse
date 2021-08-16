package expanse.common.numeric;

import expanse.common.numeric.error.NumericValueException;

import java.util.Arrays;

public class I16 extends ISize {

    public I16(byte[] bytes) {
        super(bytes);
        if (bytes.length != 2) {
            throw new NumericValueException("the length of bytes must be 2.");
        }
    }


    private I16(String value, int radix) { super(NumericUtil.parse(value, radix, 2)); }

    public static I16 valueOf(byte[] bytes) { return new I16(bytes); }
    public static I16 valueOf(String value, int radix) { return new I16(value, radix); }
    public static I16 binaryValueOf(String value) { return new I16(value, 2); }
    public static I16 hexValueOf(String value) { return new I16(value, 16); }
    public static I16 valueOf(int value) { return new I16(new byte[]{ ((byte) ((value & 0xFF00) >> 8)), (byte) value }); } // 精度损失
    public static I16 valueOf(long value) { return new I16(new byte[]{ ((byte) ((value & 0xFF00) >> 8)), (byte) value }); } // 精度损失

    public I8     i8() { return   I8.valueOf(NumericUtil.padding((byte) 0,  1, bytes)); }
    public I32   u32() { return  I32.valueOf(NumericUtil.padding((byte) 0,  4, bytes)); }
    public I64   u64() { return  I64.valueOf(NumericUtil.padding((byte) 0,  8, bytes)); }
    public I128 u128() { return I128.valueOf(NumericUtil.padding((byte) 0, 16, bytes)); }
    public I256 u256() { return I256.valueOf(NumericUtil.padding((byte) 0, 32, bytes)); }
    public I32   s32() { return  I32.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0,  4, bytes)); }
    public I64   s64() { return  I64.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0,  8, bytes)); }
    public I128 s128() { return I128.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0, 16, bytes)); }
    public I256 s256() { return I256.valueOf(NumericUtil.padding(isNegation() ? (byte) -1 : 0, 32, bytes)); }

    @Override public I16 copy() { return new I16(bytes); }

    @Override
    public String toString() {
        return "I16{" +
                "bytes=" + Arrays.toString(bytes) +
                '}';
    }

}
