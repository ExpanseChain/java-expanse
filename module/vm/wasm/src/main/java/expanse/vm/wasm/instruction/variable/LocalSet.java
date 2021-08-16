package expanse.vm.wasm.instruction.variable;

import expanse.common.numeric.ISize;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.LocalIndex;

public class LocalSet implements LocalOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, LocalIndex.class);

        LocalIndex a = (LocalIndex) args;

        int index = mi.getFrameOffset() + a.unsigned().intValue();

        ISize value = mi.popISize();

        mi.setOperand(index, value);
    }

}
