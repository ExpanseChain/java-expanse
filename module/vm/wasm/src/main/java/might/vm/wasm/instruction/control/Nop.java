package might.vm.wasm.instruction.control;

import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;

public class Nop implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) { }

}
