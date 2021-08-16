package expanse.vm.wasm.instruction.variable;

import expanse.common.numeric.ISize;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.GlobalIndex;

public class GlobalGet implements GlobalOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, GlobalIndex.class);

        GlobalIndex a = (GlobalIndex) args;

        ISize value = mi.getGlobal(a).get();

        mi.pushISize(value);
    }

}
