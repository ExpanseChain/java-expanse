package might.vm.wasm.model;

import might.common.numeric.I32;
import might.vm.wasm.model.type.ValueType;

public class Local {

    public final I32 size;        // 本地变量长度
    public final ValueType type;  // 本地变量类型

    public Local(I32 size, ValueType type) {
        this.size = size;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Local{" +
                "size=" + size +
                ", type=" + type +
                '}';
    }

}
