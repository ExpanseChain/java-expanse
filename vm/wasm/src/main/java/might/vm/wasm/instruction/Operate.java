package might.vm.wasm.instruction;

import might.vm.wasm.model.Dump;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.core.WasmReader;

public interface Operate {

    default Dump read(WasmReader reader) {
        throw new RuntimeException("what a operate code args ?");
    }

    default void operate(ModuleInstance mi, Dump args) {
        throw new RuntimeException("what a operate code ?");
    }

}
