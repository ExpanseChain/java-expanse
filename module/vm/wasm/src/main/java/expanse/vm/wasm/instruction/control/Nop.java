package expanse.vm.wasm.instruction.control;

import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class Nop implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) { }

}
