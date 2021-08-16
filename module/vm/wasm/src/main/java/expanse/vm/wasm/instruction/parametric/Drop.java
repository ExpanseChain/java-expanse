package expanse.vm.wasm.instruction.parametric;

import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class Drop implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        mi.popISize();
    }

}
