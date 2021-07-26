package might.vm.wasm.model.section;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.Limits;
import might.vm.wasm.model.type.ReferenceType;

import static might.vm.wasm.util.ConstNumber.TABLE_MAX_CAPACITY;

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
        if (limits.getMin().unsigned().longValue() > TABLE_MAX_CAPACITY) {
            throw new ModuleException(String.format("min memory page(%d) > max page count(%d)", limits.getMin().unsigned().longValue(), TABLE_MAX_CAPACITY));
        }
        if (null != limits.getMax()) {
            if (limits.getMax().unsigned().longValue() > TABLE_MAX_CAPACITY) {
                throw new ModuleException(String.format("max memory page(%d) > max page count(%d)", limits.getMax().unsigned().longValue(), TABLE_MAX_CAPACITY));
            }
            if (limits.getMin().unsigned().longValue() > limits.getMax().unsigned().longValue()) {
                throw new ModuleException(String.format("min memory page(%d) > min memory page(%d)", limits.getMin().unsigned().longValue(), limits.getMax().unsigned().longValue()));
            }
        }
    }

    public boolean same(TableType o) {
        return type == o.type && limits.same(o.limits);
    }

}
