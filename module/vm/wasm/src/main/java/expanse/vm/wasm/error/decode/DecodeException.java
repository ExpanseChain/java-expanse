package expanse.vm.wasm.error.decode;

import expanse.vm.wasm.error.WasmException;

public class DecodeException extends WasmException {

    public DecodeException(String message) {
        super(message);
    }

}
