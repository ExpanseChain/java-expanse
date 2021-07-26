package might.vm.wasm.instruction;

import might.vm.wasm.core.WasmReader;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.model.Dump;

public interface Operate {

    default Dump read(WasmReader reader) {
        throw new ModuleException("what a operate code args ?");
    }

    default void operate(ModuleInstance mi, Dump args) {
        throw new ModuleException("what a operate code ?");
    }

}
