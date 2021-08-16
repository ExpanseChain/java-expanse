package expanse.common.numeric;

import expanse.common.numeric.error.NumericNullException;
import expanse.common.numeric.error.NumericValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumericUtilTest {

    @Test
    public void testParse() {
        Assertions.assertThrows(NumericNullException.class, () -> NumericUtil.parse(null, 1, 2));
        Assertions.assertThrows(NumericValueException.class, () -> NumericUtil.parse("-1", 1, 2));
        Assertions.assertThrows(NumericValueException.class, () -> NumericUtil.parse("G", 1, 2));
        Assertions.assertThrows(NumericValueException.class, () -> NumericUtil.parse("1", 2, 0));
        Assertions.assertThrows(NumericValueException.class, () -> NumericUtil.parse("11111111", 2, 2));

        Assertions.assertArrayEquals(new byte[]{-1}, NumericUtil.parse("11111111", 2, 1));
        Assertions.assertArrayEquals(new byte[]{-1,-1}, NumericUtil.parse("1111111111111111", 2, 2));
        Assertions.assertArrayEquals(new byte[]{-1,-1,-1}, NumericUtil.parse("111111111111111111111111", 2, 0));

        Assertions.assertArrayEquals(new byte[]{-1}, NumericUtil.parse("FF", 16, 1));
        Assertions.assertArrayEquals(new byte[]{-1,-1}, NumericUtil.parse("FFFF", 16, 2));
        Assertions.assertArrayEquals(new byte[]{-1,-1,-1}, NumericUtil.parse("FFFFFF", 16, 0));

        Assertions.assertThrows(NumericValueException.class, () -> NumericUtil.parse("FF", 16, 2));

        Assertions.assertArrayEquals(new byte[]{1}, NumericUtil.parse("00000001", 2, 1));
        Assertions.assertArrayEquals(new byte[]{1,1}, NumericUtil.parse("0000000100000001", 2, 2));
        Assertions.assertArrayEquals(new byte[]{1,1,1}, NumericUtil.parse("000000010000000100000001", 2, 0));

        Assertions.assertArrayEquals(new byte[]{1}, NumericUtil.parse("01", 16, 1));
        Assertions.assertArrayEquals(new byte[]{1,1}, NumericUtil.parse("0101", 16, 2));
        Assertions.assertArrayEquals(new byte[]{1,1,1}, NumericUtil.parse("010101", 16, 0));
    }

    @Test
    public void testHexString() {
        Assertions.assertEquals("01", NumericUtil.hexString((byte) 1));
        Assertions.assertEquals("FF", NumericUtil.hexString((byte) -1));
        Assertions.assertEquals("00", NumericUtil.hexString((byte) 0));
    }

    @Test
    public void testBinaryString() {
        Assertions.assertEquals("00000001", NumericUtil.binaryString((byte) 1));
        Assertions.assertEquals("11111111", NumericUtil.binaryString((byte) -1));
        Assertions.assertEquals("00000000", NumericUtil.binaryString((byte) 0));
    }

    @Test
    public void testHexArray() {
        Assertions.assertEquals("0101", NumericUtil.toHexArray(new byte[]{(byte) 1, (byte) 1}));
        Assertions.assertEquals("FFFF", NumericUtil.toHexArray(new byte[]{(byte) -1, (byte) -1}));
        Assertions.assertEquals("0000", NumericUtil.toHexArray(new byte[]{(byte) 0, (byte) 0}));
    }

    @Test
    public void testBinaryArray() {
        Assertions.assertEquals("0000000100000001", NumericUtil.toBinaryArray(new byte[]{(byte) 1, (byte) 1}));
        Assertions.assertEquals("1111111111111111", NumericUtil.toBinaryArray(new byte[]{(byte) -1, (byte) -1}));
        Assertions.assertEquals("0000000000000000", NumericUtil.toBinaryArray(new byte[]{(byte) 0, (byte) 0}));
    }

    @Test
    public void testClz() {
        Assertions.assertEquals(8, NumericUtil.clz(new byte[]{(byte) 0, (byte) -1}));
        Assertions.assertEquals(7, NumericUtil.clz(new byte[]{(byte) 1, (byte) -1}));
        Assertions.assertEquals(0, NumericUtil.clz(new byte[]{(byte) -1, (byte) -1}));
    }

    @Test
    public void testCtz() {
        Assertions.assertEquals(8, NumericUtil.ctz(new byte[]{(byte) 1, (byte) 0}));
        Assertions.assertEquals(7, NumericUtil.ctz(new byte[]{(byte) 1, (byte) 0x80}));
        Assertions.assertEquals(0, NumericUtil.ctz(new byte[]{(byte) -1, (byte) -1}));
    }

    @Test
    public void testPopcnt() {
        Assertions.assertEquals(1, NumericUtil.popcnt(new byte[]{(byte) 1, (byte) 0}));
        Assertions.assertEquals(2, NumericUtil.popcnt(new byte[]{(byte) 1, (byte) 0x80}));
        Assertions.assertEquals(16, NumericUtil.popcnt(new byte[]{(byte) -1, (byte) -1}));
    }

    @Test
    public void testParseBytes() {
        Assertions.assertArrayEquals(new byte[]{0,0,0,0}, NumericUtil.parseBytes(0));
        Assertions.assertArrayEquals(new byte[]{-1,-1,-1,-1}, NumericUtil.parseBytes(-1));
        Assertions.assertArrayEquals(new byte[]{0,0,0,5}, NumericUtil.parseBytes(5));
        Assertions.assertArrayEquals(new byte[]{-1,-1,-1,-5}, NumericUtil.parseBytes(-5));

        Assertions.assertArrayEquals(new byte[]{0,0,0,0,0,0,0,0}, NumericUtil.parseBytes((long) 0));
        Assertions.assertArrayEquals(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1}, NumericUtil.parseBytes((long) -1));
        Assertions.assertArrayEquals(new byte[]{0,0,0,0,0,0,0,5}, NumericUtil.parseBytes((long) 5));
        Assertions.assertArrayEquals(new byte[]{-1,-1,-1,-1,-1,-1,-1,-5}, NumericUtil.parseBytes((long) -5));
    }

    @Test
    public void testPadding() {
        Assertions.assertArrayEquals(new byte[]{0,0,0,0}, NumericUtil.padding((byte) 0, 4, new byte[]{0}));
        Assertions.assertArrayEquals(new byte[]{-1,-1,-1,-1}, NumericUtil.padding((byte) -1, 4, new byte[]{-1}));
        Assertions.assertArrayEquals(new byte[]{0,0,0,5}, NumericUtil.padding((byte) 0, 4, new byte[]{5}));
    }

}
