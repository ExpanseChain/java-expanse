package might.vm.wasm.util;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.model.Validate;

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
