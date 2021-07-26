package might.vm.wasm.error.assertion;

import might.vm.wasm.error.WasmException;

public class AssertionTypeException extends WasmException {

    public AssertionTypeException(String message) {
        super(message);
    }

}
