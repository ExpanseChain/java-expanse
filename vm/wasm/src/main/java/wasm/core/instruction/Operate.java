package wasm.core.instruction;

import wasm.core.model.Dump;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;

public interface Operate {

    default Dump read(WasmReader reader) {
        throw new RuntimeException("what a operate code args ?");
    }

    default void operate(ModuleInstance mi, Dump args) {
        throw new RuntimeException("what a operate code ?");
    }

}
