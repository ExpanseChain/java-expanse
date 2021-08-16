package expanse.vm.wasm.instruction.numeric.i32.compare;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class I32Eq implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I32 v2 = mi.popI32();
        I32 v1 = mi.popI32();
        mi.pushBool(v1.equals(v2));
    }

}
