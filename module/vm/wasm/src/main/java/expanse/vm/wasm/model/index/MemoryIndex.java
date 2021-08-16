package expanse.vm.wasm.model.index;

import expanse.common.numeric.I32;

public class MemoryIndex extends U32 {

    private MemoryIndex(I32 i32) {
        super(i32);
    }

    public static MemoryIndex of(I32 value) { return new MemoryIndex(value); }

    public String dump(int index) {
        return "mem[" + index + "]: " + "value=" + unsigned();
    }

}
