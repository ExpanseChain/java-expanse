package might.vm.wasm.instruction;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.decode.DecodeException;
import might.vm.wasm.error.execute.ExecutionException;
import might.vm.wasm.model.Dump;

public interface Operate {

    default Dump read(WasmReader reader) {
        throw new DecodeException("what a operate code args ?");
    }

    default void valid(ModuleInfo info, Dump args, int parameters, long locals) {
        throw new DecodeException("how to valid ?");
    }

    default void operate(ModuleInstance mi, Dump args) {
        throw new ExecutionException("what a operate code ?");
    }

}
