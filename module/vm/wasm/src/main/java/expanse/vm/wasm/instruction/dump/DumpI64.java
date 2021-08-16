package expanse.vm.wasm.instruction.dump;

import expanse.vm.wasm.model.Dump;

public class DumpI64 implements Dump {

    public long value;

    public DumpI64(long value) {
        this.value = value;
    }

    @Override
    public String dump() {
        return String.valueOf(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
