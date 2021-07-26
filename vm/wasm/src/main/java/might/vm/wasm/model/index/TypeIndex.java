package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.model.Validate;
import might.vm.wasm.util.Slice;

public class TypeIndex extends U32 implements Validate {

    private TypeIndex(I32 i32) {
        super(i32);
    }

    public static TypeIndex of(I32 value) { return new TypeIndex(value); }

    public String dump(int index) {
        return "func[" + index + "]: " + "value=" + unsigned();
    }

    @Override
    public void validate(ModuleInfo info) {
        // 检查能找到对应的函数签名就可以了
        int i = unsigned().intValue();
        Slice.checkArrayIndex(i);
        if (info.typeSections.size() <= i) {
            throw new ModuleException("can not find function type by index: " + i);
        }
    }
}
