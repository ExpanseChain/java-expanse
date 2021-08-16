package expanse.vm.wasm.model.section;

import expanse.vm.wasm.instruction.Action;
import expanse.vm.wasm.instruction.Expression;
import expanse.vm.wasm.model.type.ValueType;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.decode.DecodeException;
import expanse.vm.wasm.model.Validate;
import expanse.vm.wasm.model.type.GlobalType;

public class GlobalSection implements Validate {

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
    public void validate(ModuleInfo info) {
        // 表达式要检查一下
        ValueType vt = null;
        for (Action action : init) {
            switch (action.getInstruction()) {
                case I32_CONST:
                    if (null != vt && vt != ValueType.I32) {
                        throw new DecodeException("global init expression error.");
                    }
                    vt = ValueType.I32;
                    break;
                case I64_CONST:
                    if (null != vt && vt != ValueType.I64) {
                        throw new DecodeException("global init expression error.");
                    }
                    vt = ValueType.I64;
                    break;
                case GLOBAL_GET: break;
                default: throw new DecodeException("global init expression error.");
            }
        }
        if (vt != type.value) {
            throw new DecodeException("global init expression error.");
        }
    }

}
