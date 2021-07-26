package might.vm.wasm.model.section;

import might.common.numeric.I32;
import might.vm.wasm.model.Limits;
import might.vm.wasm.model.tag.LimitsTag;

public class MemoryType extends Limits {

    public MemoryType(LimitsTag tag, I32 min, I32 max) {
        super(tag, min, max);
    }

    public String dump(int index) {
        return "memory[" + index + "]: " + dump();
    }

}
