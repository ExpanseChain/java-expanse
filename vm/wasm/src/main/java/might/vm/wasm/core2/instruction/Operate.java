package might.vm.wasm.core2.instruction;

import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public interface Operate {

    default Dump read(WasmReader reader) {
        throw new RuntimeException("what a operate code args ?");
    }

    default void operate(ModuleInstance mi, Dump args) {
        throw new RuntimeException("what a operate code ?");
    }

}