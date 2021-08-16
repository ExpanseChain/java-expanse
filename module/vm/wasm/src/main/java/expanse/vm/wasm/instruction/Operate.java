package expanse.vm.wasm.instruction;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.decode.DecodeException;
import expanse.vm.wasm.error.execute.ExecutionException;
import expanse.vm.wasm.model.Dump;

public interface Operate {

    default Dump read(WasmReader reader) {
        throw new DecodeException("what a operate code args ?");
    }

    default void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        throw new DecodeException("how to valid ?");
    }

    default void operate(ModuleInstance mi, Dump args) {
        throw new ExecutionException("what a operate code ?");
    }

}
