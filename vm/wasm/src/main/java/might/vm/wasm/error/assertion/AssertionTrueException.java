package might.vm.wasm.core.error.assertion;

import might.vm.wasm.core.error.WasmException;

public class AssertionTrueException extends WasmException {

    public AssertionTrueException(String message) {
        super(message);
    }

}
