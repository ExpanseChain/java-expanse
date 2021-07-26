package might.vm.wasm.core.error.assertion;

import might.vm.wasm.core.error.WasmException;

public class AssertionTypeException extends WasmException {

    public AssertionTypeException(String message) {
        super(message);
    }

}
