package might.vm.wasm.core2.model.section;

import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.model.Limits;
import might.vm.wasm.core2.model.type.ReferenceType;

public class TableType implements Dump {

    public final ReferenceType type;    // 引用类型
    public final Limits limits;         // 表限制

    public TableType(ReferenceType type, Limits limits) {
        this.type = type;
        this.limits = limits;
    }

    @Override
    public String toString() {
        return "TableType{" +
                "type=" + type +
                ", limits=" + limits +
                '}';
    }

    public String dump(int index) {
        return "table[" + index + "]: " + dump();
    }

    @Override
    public String dump() {
        return type.dump() + " " + limits.dump();
    }
}
