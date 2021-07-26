package might.vm.wasm.core2.instruction.dump;

import might.vm.wasm.model.Dump;

import static might.vm.wasm.util.NumberTransform.toHex;


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
