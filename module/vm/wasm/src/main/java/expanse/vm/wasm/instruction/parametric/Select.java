package expanse.vm.wasm.instruction.parametric;

import expanse.common.numeric.ISize;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class Select implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        boolean v3 = mi.popBool();
        ISize v2 = mi.popISize();
        ISize v1 = mi.popISize();
        if (v3) {
            mi.pushISize(v1);
        } else {
            mi.pushISize(v2);
        }
    }

}
