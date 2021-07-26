package might.vm.wasm.util;

import might.common.numeric.I32;
import might.common.numeric.I64;

import java.math.BigInteger;

import static might.vm.wasm.util.NumberTransform.*;

/**
 * 数字符号工具
 */
public class NumberUtil {



    public static int divS(int a, int b) {
        return a / b;
    }

    public static long divS(long a, long b) {
        return a / b;
    }

    public static I32 divU(I32 a, I32 b) {
        return I32.valueOf(parse(a.unsigned().divide(b.unsigned()), 4));
    }

    public static I64 divU(I64 a, I64 b) {
        return I64.valueOf(parse(a.unsigned().divide(b.unsigned()), 8));
    }

    public static int remS(int a, int b) {
        return a % b;
    }

    public static long remS(long a, long b) {
        return a % b;
    }


    public static I32 remU(I32 a, I32 b) {
        return I32.valueOf(parse(a.unsigned().remainder(b.unsigned()), 4));
    }

    public static I64 remU(I64 a, I64 b) {
        return I64.valueOf(parse(a.unsigned().remainder(b.unsigned()), 8));
    }

    public static I32 and(I32 a, I32 b) {
        return I32.valueOf(new byte[] {
            (byte) (a.bytes()[0] & b.bytes()[0]),
            (byte) (a.bytes()[1] & b.bytes()[1]),
            (byte) (a.bytes()[2] & b.bytes()[2]),
            (byte) (a.bytes()[3] & b.bytes()[3])
        });
    }

    public static I64 and(I64 a, I64 b) {
        return I64.valueOf(new byte[] {
            (byte) (a.bytes()[0] & b.bytes()[0]),
            (byte) (a.bytes()[1] & b.bytes()[1]),
            (byte) (a.bytes()[2] & b.bytes()[2]),
            (byte) (a.bytes()[3] & b.bytes()[3]),
            (byte) (a.bytes()[4] & b.bytes()[4]),
            (byte) (a.bytes()[5] & b.bytes()[5]),
            (byte) (a.bytes()[6] & b.bytes()[6]),
            (byte) (a.bytes()[7] & b.bytes()[7])
        });
    }

    public static I32 or(I32 a, I32 b) {
        return I32.valueOf(new byte[] {
                (byte) (a.bytes()[0] | b.bytes()[0]),
                (byte) (a.bytes()[1] | b.bytes()[1]),
                (byte) (a.bytes()[2] | b.bytes()[2]),
                (byte) (a.bytes()[3] | b.bytes()[3])
        });
    }

    public static I64 or(I64 a, I64 b) {
        return I64.valueOf(new byte[] {
                (byte) (a.bytes()[0] | b.bytes()[0]),
                (byte) (a.bytes()[1] | b.bytes()[1]),
                (byte) (a.bytes()[2] | b.bytes()[2]),
                (byte) (a.bytes()[3] | b.bytes()[3]),
                (byte) (a.bytes()[4] | b.bytes()[4]),
                (byte) (a.bytes()[5] | b.bytes()[5]),
                (byte) (a.bytes()[6] | b.bytes()[6]),
                (byte) (a.bytes()[7] | b.bytes()[7])
        });
    }


    public static I32 xor(I32 a, I32 b) {
        return I32.valueOf(new byte[] {
                (byte) (a.bytes()[0] ^ b.bytes()[0]),
                (byte) (a.bytes()[1] ^ b.bytes()[1]),
                (byte) (a.bytes()[2] ^ b.bytes()[2]),
                (byte) (a.bytes()[3] ^ b.bytes()[3])
        });
    }

    public static I64 xor(I64 a, I64 b) {
        return I64.valueOf(new byte[] {
                (byte) (a.bytes()[0] ^ b.bytes()[0]),
                (byte) (a.bytes()[1] ^ b.bytes()[1]),
                (byte) (a.bytes()[2] ^ b.bytes()[2]),
                (byte) (a.bytes()[3] ^ b.bytes()[3]),
                (byte) (a.bytes()[4] ^ b.bytes()[4]),
                (byte) (a.bytes()[5] ^ b.bytes()[5]),
                (byte) (a.bytes()[6] ^ b.bytes()[6]),
                (byte) (a.bytes()[7] ^ b.bytes()[7])
        });
    }






    public static I32 shl(I32 a, I32 b) {
        int length = b.signed().remainder(BigInteger.valueOf(32)).intValue();
        String v = toBinaryArray(a.bytes());
        return I32.valueOf(v.substring(length) + zeros(length), 2);
    }

    public static I64 shl(I64 a, I64 b) {
        int length = b.signed().remainder(BigInteger.valueOf(64)).intValue();
        String v = toBinaryArray(a.bytes());
        return I64.valueOf(v.substring(length) + zeros(length), 2);
    }

    public static I32 shrS(I32 a, I32 b) {
        int length = b.signed().remainder(BigInteger.valueOf(32)).intValue();
        String v = toBinaryArray(a.bytes());
        return I32.valueOf((v.charAt(0) == '1' ? ones(length) : zeros(length))
                +  v.substring(0, v.length() - length), 2);
    }

    public static I64 shrS(I64 a, I64 b) {
        int length = b.signed().remainder(BigInteger.valueOf(64)).intValue();
        String v = toBinaryArray(a.bytes());
        return I64.valueOf((v.charAt(0) == '1' ? ones(length) : zeros(length))
                +  v.substring(0, v.length() - length), 2);
    }

    public static I32 shrU(I32 a, I32 b) {
        int length = b.signed().remainder(BigInteger.valueOf(32)).intValue();
        String v = toBinaryArray(a.bytes());
        return I32.valueOf(zeros(length) +  v.substring(0, v.length() - length), 2);
    }

    public static I64 shrU(I64 a, I64 b) {
        int length = b.signed().remainder(BigInteger.valueOf(64)).intValue();
        String v = toBinaryArray(a.bytes());
        return I64.valueOf(zeros(length) +  v.substring(0, v.length() - length), 2);
    }

    public static I32 rotl(I32 a, I32 b) {
        int length = b.signed().remainder(BigInteger.valueOf(32)).intValue();
        String v = toBinaryArray(a.bytes());
        return I32.valueOf(v.substring(length) + v.substring(0, length), 2);
    }

    public static I64 rotl(I64 a, I64 b) {
        int length = b.signed().remainder(BigInteger.valueOf(64)).intValue();
        String v = toBinaryArray(a.bytes());
        return I64.valueOf(v.substring(length) + v.substring(0, length), 2);
    }


    public static I32 rotr(I32 a, I32 b) {
        int length = b.signed().remainder(BigInteger.valueOf(32)).intValue();
        String v = toBinaryArray(a.bytes());
        return I32.valueOf(v.substring(v.length() - length) + v.substring(0, v.length() - length), 2);
    }

    public static I64 rotr(I64 a, I64 b) {
        int length = b.signed().remainder(BigInteger.valueOf(64)).intValue();
        String v = toBinaryArray(a.bytes());
        return I64.valueOf(v.substring(v.length() - length) + v.substring(0, v.length() - length), 2);
    }

}
