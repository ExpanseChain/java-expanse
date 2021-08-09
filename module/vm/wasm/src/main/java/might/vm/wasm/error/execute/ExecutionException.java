package might.vm.wasm.error.execute;

import might.vm.wasm.error.WasmException;

public class ExecutionException extends WasmException {
    public ExecutionException(String message) {
        super(message);
    }
}
