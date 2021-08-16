package expanse.vm.wasm.instruction.control;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.ControlFrame;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.Instruction;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.LabelIndex;

public class Return implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {

        ControlFrame frame = mi.topCallFrame();

        Instruction.BR.operate(mi, LabelIndex.of(I32.valueOf(frame.getDepth())));
    }
}
