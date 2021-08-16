package expanse.vm.wasm.error.module;

import expanse.vm.wasm.error.WasmException;

public class ModuleException extends WasmException {

    public ModuleException(String message) {
        super(message);
    }

}
