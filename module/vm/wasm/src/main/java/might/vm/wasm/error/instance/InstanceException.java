package might.vm.wasm.error.instance;

import might.vm.wasm.error.WasmException;

public class InstanceException extends WasmException {
    public InstanceException(String message) {
        super(message);
    }
}
