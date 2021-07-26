package might.vm.wasm.instruction.control;

import might.common.numeric.I32;
import might.vm.wasm.core.ControlFrame;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.LabelIndex;

public class Return implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {

        ControlFrame frame = mi.topCallFrame();

        Instruction.BR.operate(mi, LabelIndex.of(I32.valueOf(frame.getDepth())));
    }
}
