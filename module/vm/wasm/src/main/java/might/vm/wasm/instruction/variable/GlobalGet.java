package might.vm.wasm.instruction.variable;

import expanse.common.numeric.ISize;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.GlobalIndex;

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
