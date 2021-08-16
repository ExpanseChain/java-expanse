package expanse.vm.wasm.instruction.variable;

import expanse.common.numeric.ISize;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.GlobalIndex;

public class GlobalSet implements GlobalOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, GlobalIndex.class);

        GlobalIndex index = (GlobalIndex) args;

        ISize value = mi.popISize();

        mi.getGlobal(index).set(value);
    }

}
