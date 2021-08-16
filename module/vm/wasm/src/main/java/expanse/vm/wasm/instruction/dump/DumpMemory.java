package expanse.vm.wasm.instruction.dump;

import expanse.common.numeric.I32;
import expanse.vm.wasm.model.Dump;

public class DumpMemory implements Dump {

    private final I32 align;
    private final I32 offset;

    public DumpMemory(I32 align, I32 offset) {
        this.align = align;
        this.offset = offset;
    }

    public I32 getOffset() {
        return offset;
    }

    @Override
    public String dump() {
        return String.format("{align: %s, offset: %s}", align.unsigned(), offset.unsigned());
    }

    @Override
    public String toString() {
        return dump();
    }

}
