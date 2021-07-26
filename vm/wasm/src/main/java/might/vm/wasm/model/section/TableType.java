package might.vm.wasm.model.section;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.Limits;
import might.vm.wasm.model.type.ReferenceType;

public class TableType implements Dump, Valid {

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

    @Override
    public void valid(ModuleInfo info) {
        // 表类型 验证 ？
    }
}
