package wasm.core.instruction.control;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;

public class Nop implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) { }

}
