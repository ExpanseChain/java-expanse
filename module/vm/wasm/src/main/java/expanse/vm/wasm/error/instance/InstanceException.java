package expanse.vm.wasm.error.instance;

import expanse.vm.wasm.error.WasmException;

public class InstanceException extends WasmException {
    public InstanceException(String message) {
        super(message);
    }
}
