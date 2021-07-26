package might.vm.wasm.core2.instruction.dump;

import might.vm.wasm.core.error.Assertions;
import might.vm.wasm.core2.model.Dump;

import static might.vm.wasm.util.NumberTransform.toHex;

public class DumpZero implements Dump {

    public byte value;

    public DumpZero(byte value) {
        Assertions.requireTrue(value == 0x00);
        this.value = value;
    }

    @Override
    public String dump() {
        return toHex(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
