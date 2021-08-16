package expanse.vm.wasm.instruction.dump;

import expanse.vm.wasm.model.Dump;

import static expanse.vm.wasm.util.NumberTransform.toHex;


public class DumpByte implements Dump {

    public byte value;

    public DumpByte(byte value) {
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
