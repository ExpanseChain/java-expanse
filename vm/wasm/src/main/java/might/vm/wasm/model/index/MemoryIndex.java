package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.model.Dump;

public class MemoryIndex extends I32 implements Dump {

    private MemoryIndex(I32 i32) {
        super(i32.bytes());
    }

    public static MemoryIndex of(I32 value) { return new MemoryIndex(value); }

    public String dump(int index) {
        return "mem[" + index + "]: " + "value=" + unsigned();
    }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
