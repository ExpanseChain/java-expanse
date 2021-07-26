package might.vm.wasm.model.section;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.instruction.Expression;
import might.vm.wasm.model.type.GlobalType;

public class GlobalSection implements Valid {

    public GlobalType type; // 变量类型
    public Expression init; // 初始化表达式

    public GlobalSection(GlobalType type, Expression init) {
        this.type = type;
        this.init = init;
    }

    @Override
    public String toString() {
        return "Global{" +
                "type=" + type +
                ", init=" + init +
                '}';
    }

    public String dump(int index) {
        return "global[" + index + "]: " + type.dump() + " " + init.dump();
    }

    @Override
    public void valid(ModuleInfo info) {
        // 表达式要检查一下
        init.valid(info, 0,0);
    }

}
