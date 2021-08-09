package might.vm.wasm.instruction.parametric;

import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;

public class Drop implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        mi.popISize();
    }

}
