package wasm.core.instruction.numeric;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;

public class I64ExtendI32S implements Operate {
    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        mi.pushS64(mi.popS32());
    }

}
