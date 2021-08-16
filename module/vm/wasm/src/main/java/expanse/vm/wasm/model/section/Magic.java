package expanse.vm.wasm.model.section;

import expanse.vm.wasm.error.decode.DecodeException;

public class Magic {

    private static final byte b0 = 0x00;
    private static final byte b1 = 0x61;
    private static final byte b2 = 0x73;
    private static final byte b3 = 0x6D;

    private final String value;

    public Magic(byte b0, byte b1, byte b2, byte b3) {
        if (Magic.b0 != b0) { throw new DecodeException("magic wrong"); }
        if (Magic.b1 != b1) { throw new DecodeException("magic wrong"); }
        if (Magic.b2 != b2) { throw new DecodeException("magic wrong"); }
        if (Magic.b3 != b3) { throw new DecodeException("magic wrong"); }

        this.value = new String(new byte[]{ b0, b1, b2, b3 });
    }

    public String value() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Magic: '" + value + "'";
    }

}
