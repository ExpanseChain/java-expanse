package expanse.vm.wasm.error.execute;

import expanse.vm.wasm.error.WasmException;

public class ExecutionException extends WasmException {
    public ExecutionException(String message) {
        super(message);
    }
}
