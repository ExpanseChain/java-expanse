package might.vm.wasm.model.describe;

import might.common.numeric.I32;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.TypeIndex;
import might.vm.wasm.model.section.MemoryType;
import might.vm.wasm.model.section.TableType;
import might.vm.wasm.model.section.Valid;
import might.vm.wasm.model.tag.PortTag;
import might.vm.wasm.model.type.GlobalType;

public class ImportDescribe {

    public final PortTag tag;

    public final Value value;

    public static abstract class Value implements Dump, Valid { }

    public static class Function extends Value {
        public TypeIndex typeIndex; // 如果是导入函数 指向类型段的函数索引

        public Function(TypeIndex typeIndex) {
            this.typeIndex = typeIndex;
        }

        @Override
        public String dump() { return typeIndex.unsigned().toString(); }

        @Override
        public void valid(ModuleInfo info) {
            if (typeIndex.greaterOrEqualsU(I32.valueOf(info.typeSections.size()))) {
                // type index >= size
                throw new ModuleException("can not find type section by index: " + typeIndex.unsigned().toString());
            }
        }
    }
    public static class Table extends Value {
        public TableType table; // 如果是导入表 表信息

        public Table(TableType table) {
            this.table = table;
        }

        @Override
        public String dump() { return table.dump(); }

        @Override
        public void valid(ModuleInfo info) {
            table.valid(info);
        }
    }
    public static class Memory extends Value {
        public MemoryType memory; // 如果是导入内存 内存信息

        public Memory(MemoryType memory) {
            this.memory = memory;
        }

        @Override
        public String dump() { return memory.dump(); }

        @Override
        public void valid(ModuleInfo info) {
            memory.valid(info);
        }
    }
    public static class Global extends Value {
        public GlobalType global; // 如果是导入全局变量 变量信息

        public Global(GlobalType global) {
            this.global = global;
        }

        @Override
        public String dump() { return global.dump(); }

        @Override
        public void valid(ModuleInfo info) {
            // 构造就算验证了
        }
    }


    public ImportDescribe(PortTag tag, Value value) {
        this.tag = tag;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ImportDescribe{" +
                "tag=" + tag +
                ", value=" + value +
                '}';
    }

}
