package might.vm.wasm.model.section;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.error.decode.DecodeException;
import might.vm.wasm.instruction.Action;
import might.vm.wasm.instruction.Expression;
import might.vm.wasm.model.Validate;
import might.vm.wasm.model.type.GlobalType;
import might.vm.wasm.model.type.ValueType;

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
