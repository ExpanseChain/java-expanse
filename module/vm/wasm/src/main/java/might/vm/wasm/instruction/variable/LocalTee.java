package might.vm.wasm.instruction.variable;

import might.common.numeric.ISize;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.LocalIndex;

public class LocalTee implements LocalOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, LocalIndex.class);

        LocalIndex a = (LocalIndex) args;

        int index = mi.getFrameOffset() + a.unsigned().intValue();

        ISize value = mi.popISize();

        mi.pushISize(value); // 再压回去

        mi.setOperand(index, value);
    }

}