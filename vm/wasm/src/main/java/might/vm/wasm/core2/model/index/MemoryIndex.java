package might.vm.wasm.core2.model.index;

import might.vm.wasm.core2.numeric.U32;

public class MemoryIndex extends U32 {

    private MemoryIndex(U32 u32) {
        super(u32);
    }

    private MemoryIndex(int value) {
        super(value);
    }

    public static MemoryIndex of(U32 value) { return new MemoryIndex(value); }

    public static MemoryIndex of(int value) { return new MemoryIndex(value); }

    public String dump(int index) {
        return "mem[" + index + "]: " + "value=" + super.toString();
    }

}
