package expanse.vm.wasm.instruction.numeric.i32.compare;

import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class I32Eqz implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        mi.pushBool(!mi.popI32().booleanValue());
    }

}
