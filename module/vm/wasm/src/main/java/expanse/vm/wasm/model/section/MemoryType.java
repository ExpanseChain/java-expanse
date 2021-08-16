package expanse.vm.wasm.model.section;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.module.ModuleException;
import expanse.vm.wasm.model.Limits;
import expanse.vm.wasm.model.Validate;
import expanse.vm.wasm.model.tag.LimitsTag;

import static expanse.vm.wasm.util.ConstNumber.MEMORY_MAX_PAGE_COUNT;

public class MemoryType extends Limits implements Validate {

    public MemoryType(LimitsTag tag, I32 min, I32 max) {
        super(tag, min, max);
    }

    public String dump(int index) {
        return "memory[" + index + "]: " + dump();
    }

    @Override
    public void validate(ModuleInfo info) {
        if (min.unsigned().longValue() > MEMORY_MAX_PAGE_COUNT) {
            throw new ModuleException(String.format("min memory page(%d) > max page count(%d)", min.unsigned().longValue(), MEMORY_MAX_PAGE_COUNT));
        }
        if (null != max) {
            if (max.unsigned().longValue() > MEMORY_MAX_PAGE_COUNT) {
                throw new ModuleException(String.format("max memory page(%d) > max page count(%d)", max.unsigned().longValue(), MEMORY_MAX_PAGE_COUNT));
            }
            if (min.unsigned().longValue() > max.unsigned().longValue()) {
                throw new ModuleException(String.format("min memory page(%d) > min memory page(%d)", min.unsigned().longValue(), max.unsigned().longValue()));
            }
        }
    }

}
