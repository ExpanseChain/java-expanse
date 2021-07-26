package might.vm.wasm.model.section;

import might.common.numeric.I32;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.model.Limits;
import might.vm.wasm.model.tag.LimitsTag;

import static might.vm.wasm.util.ConstNumber.MEMORY_MAX_PAGE_COUNT;

public class MemoryType extends Limits implements Valid {

    public MemoryType(LimitsTag tag, I32 min, I32 max) {
        super(tag, min, max);
    }

    public String dump(int index) {
        return "memory[" + index + "]: " + dump();
    }

    @Override
    public void valid(ModuleInfo info) {
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
