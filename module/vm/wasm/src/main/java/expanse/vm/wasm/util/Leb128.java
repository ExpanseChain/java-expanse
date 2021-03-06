package expanse.vm.wasm.util;

import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.error.decode.DecodeException;

import static expanse.vm.wasm.util.NumberTransform.*;

/**
 * Leb128编码解析
 */
public class Leb128 {

    /**
     * 读取结果
     */
    public static class Result {
        public final byte[] bytes;
        public final int length;
        private Result(byte[] bytes, int length) {
            this.bytes = bytes;
            this.length = length;
        }

        static Result of(String v, int length) {
            byte[] bytes = new byte[v.length() / 8];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = parseByteByBinary(v.substring(8 * i, 8 * i + 8));
            }
            return new Result(bytes, length);
        }
    }

    public static Result readLeb128U32(byte[] data) { return decodeVarUint(data, 32); }

    public static Result readLeb128U64(byte[] data) { return decodeVarUint(data, 64); }

    /**
     * 读取无符号数字
     */
    private static Result decodeVarUint(byte[] data, int size) {
        Assertions.requireNonNull(data);
        Assertions.requireTrue(data.length > 0);
        Assertions.requireTrue(size % 8 == 0);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];

            if (i == size / 7) {
                // 如果应当是最后一个字节
                if ((b & 0x80) != 0) {
                    // 已经最后一个字节 第一位标识符不应该是1 如果是1表示后面还有，不应该
                    throw new DecodeException("integer representation too long");
                }
                if (b >> (size - i * 7) > 0) {
                    // 已经最后一个字节 移除需要的数字位，剩下的应该全是0
                    throw new DecodeException("integer too large");
                }
            }

            // 每次只取后面7位数字
            sb.insert(0, toBinary(b).substring(1));

            if ((b & 0x80) == 0) {
                String v;
                if (sb.length() > size) {
                    v = sb.substring(sb.length() - size);
                } else if (sb.length() < size){
                    v = zeros(size - sb.length()) + sb;
                } else {
                    v = sb.toString();
                }
                return Result.of(v, i + 1);
            }
        }

        throw new DecodeException("read leb128 number failed.");
    }


    public static Result readLeb128S32(byte[] data) { return decodeVarInt(data, 32); }

    public static Result readLeb128S64(byte[] data) { return decodeVarInt(data, 64); }

    /**
     * 读取有符号数字
     */
    private static Result decodeVarInt(byte[] data, int size) {
        Assertions.requireNonNull(data);
        Assertions.requireTrue(data.length > 0);
        Assertions.requireTrue(size % 8 == 0);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];

            if (i == size / 7) {
                // 如果应当是最后一个字节
                if ((b&0x80) != 0) {
                    // 已经最后一个字节 第一位标识符不应该是1 如果是1表示后面还有，不应该
                    throw new DecodeException("integer representation too long");
                }
                if (((b & 0x40) == 0 && b >> (size - i * 7 - 1) != 0) ||
                    ((b & 0x40) != 0 && ((b | 0xFFFFFF80) >> (size - i * 7 - 1)) != -1)) {
                    // 0b0100_0000 末位的第2位是符号位 如果是0 则 排出需要的位 剩下的都应该是0
                    // 0b0100_0000 末位的第2位是符号位 如果是1 则 排出需要的位 剩下的都是必须都是1
                    throw new DecodeException("integer too large");
                }
            }

            // 每次只取后面7位数字
            sb.insert(0, toBinary(b).substring(1));

            if ((b&0x80) == 0) {
                String v;
                if (sb.length() > size) {
                    v = sb.substring(sb.length() - size);
                } else if (sb.length() < size){
                    if ((i * 7 < size) && ((b & 0x40) != 0)) {
                        // 如果顶位是1 则填充满为1
                        v = ones(size - sb.length()) + sb;
                    } else {
                        v = zeros(size - sb.length()) + sb;
                    }
                } else {
                    v = sb.toString();
                }
                return Result.of(v, i + 1);
            }
        }

        throw new DecodeException("read leb128 number failed.");
    }

}
