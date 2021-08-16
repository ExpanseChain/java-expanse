package expanse.vm.wasm.instruction.numeric.i64.compare;

import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class I64Eqz implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        mi.pushBool(!mi.popI64().booleanValue());
    }

}
