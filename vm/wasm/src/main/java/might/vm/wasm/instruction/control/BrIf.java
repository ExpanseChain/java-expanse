package might.vm.wasm.instruction.control;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.LabelIndex;

public class BrIf implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readLabelIndex();
    }

    @Override
    public void valid(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof LabelIndex);
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        if (mi.popBool()) {
            // 如果需要跳转
            Instruction.BR.operate(mi, args);
        }
    }
}
