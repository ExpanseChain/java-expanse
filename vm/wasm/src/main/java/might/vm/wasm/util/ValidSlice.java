package might.vm.wasm.util;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.model.section.Valid;

public class ValidSlice<T extends Valid> extends Slice<T> {
    public ValidSlice() {
        super();
    }
    public ValidSlice(int capacity) {
        super(capacity);
    }

    public ValidSlice<T> valid(ModuleInfo info) {
        for (T t : data) {
            if (null == t) {
                throw new ModuleException("can not valid null section");
            }
            t.valid(info);
        }
        return this;
    }


}
