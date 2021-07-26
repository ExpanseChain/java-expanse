package might.vm.wasm.model.section;

import might.vm.wasm.model.Limits;
import might.vm.wasm.model.tag.LimitsTag;
import might.vm.wasm.core2.numeric.U32;

public class MemoryType extends Limits {

    public MemoryType(LimitsTag tag, U32 min, U32 max) {
        super(tag, min, max);
    }

    public String dump(int index) {
        return "memory[" + index + "]: " + dump();
    }

}
