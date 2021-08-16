package expanse.vm.wasm.instruction.control;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.Instruction;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.LabelIndex;

public class BrIf implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readLabelIndex();
    }

    @Override
    public void validate(ModuleInfo info, Dump args, int parameters, long locals) {
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
