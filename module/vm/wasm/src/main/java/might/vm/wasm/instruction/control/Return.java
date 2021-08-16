package might.vm.wasm.instruction.control;

import expanse.common.numeric.I32;
import might.vm.wasm.core.ControlFrame;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.LabelIndex;

public class Return implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {

        ControlFrame frame = mi.topCallFrame();

        Instruction.BR.operate(mi, LabelIndex.of(I32.valueOf(frame.getDepth())));
    }
}
