package expanse.vm.wasm.model.section;

import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.module.ModuleException;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.Limits;
import expanse.vm.wasm.model.Validate;
import expanse.vm.wasm.model.type.ReferenceType;

import static expanse.vm.wasm.util.ConstNumber.TABLE_MAX_CAPACITY;

public class TableType implements Dump, Validate {

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
    public void validate(ModuleInfo info) {
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
