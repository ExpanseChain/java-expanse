package might.vm.wasm.error.assertion;

import might.vm.wasm.error.WasmException;

public class AssertionTrueException extends WasmException {

    public AssertionTrueException(String message) {
        super(message);
    }

}
