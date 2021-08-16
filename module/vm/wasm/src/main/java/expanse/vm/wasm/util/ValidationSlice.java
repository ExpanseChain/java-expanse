package expanse.vm.wasm.util;

import expanse.vm.wasm.error.module.ModuleException;
import expanse.vm.wasm.model.Validate;
import expanse.vm.wasm.core.ModuleInfo;

public class ValidationSlice<T extends Validate> extends Slice<T> {
    public ValidationSlice() {
        super();
    }
    public ValidationSlice(int capacity) {
        super(capacity);
    }

    public ValidationSlice<T> valid(ModuleInfo info) {
        for (T t : data) {
            if (null == t) {
                throw new ModuleException("can not valid null section");
            }
            t.validate(info);
        }
        return this;
    }


}
