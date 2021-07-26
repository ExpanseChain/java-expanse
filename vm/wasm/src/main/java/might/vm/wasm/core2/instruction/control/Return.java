package might.vm.wasm.core2.instruction.control;

import might.common.numeric.I32;
import might.vm.wasm.core2.instruction.Instruction;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.LabelIndex;

public class Return implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        int[] index = new int[1];

        mi.topCallFrame(index);

        Instruction.BR.operate(mi, LabelIndex.of(I32.valueOf(index[0])));
    }
}
