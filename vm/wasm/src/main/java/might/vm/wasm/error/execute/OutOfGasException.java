package might.vm.wasm.error.execute;

public class OutOfGasException extends ExecutionException {
    public OutOfGasException(String message) {
        super(message);
    }
}
