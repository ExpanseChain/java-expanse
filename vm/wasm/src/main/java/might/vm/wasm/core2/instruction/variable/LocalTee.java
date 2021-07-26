package might.vm.wasm.core2.instruction.variable;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.LocalIndex;
import might.vm.wasm.core2.numeric.USize;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class LocalTee implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readLocalIndex();
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, LocalIndex.class);

        LocalIndex a = (LocalIndex) args;

        int index = mi.getFrameOffset() + a.intValue();

        USize value = mi.popUSize();

        mi.pushUSize(value); // 再压回去

        mi.setOperand(index, value);
    }

}
