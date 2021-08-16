package expanse.vm.wasm.instruction.dump;

import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.model.Dump;

import static expanse.vm.wasm.util.NumberTransform.toHex;

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
