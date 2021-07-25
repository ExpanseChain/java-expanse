package wasm.core.instruction.variable;

import wasm.core.exception.Check;
import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.model.index.LocalIndex;
import wasm.core.numeric.USize;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;

public class LocalGet implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readLocalIndex();
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Check.requireNonNull(args);
        Check.require(args, LocalIndex.class);

        LocalIndex a = (LocalIndex) args;

        int index = mi.getFrameOffset() + a.intValue();

        USize value = mi.getOperand(index, USize.class);

        mi.pushUSize(value);
    }

}
