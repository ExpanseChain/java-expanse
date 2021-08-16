package expanse.vm.wasm.error;

import expanse.vm.wasm.error.decode.DecodeException;
import expanse.vm.wasm.error.execute.ExecutionException;

public class Assertions {

    public static void requireType(Object o, Class<?> c) {
        if (o.getClass() != c) {
            throw new ExecutionException("o is not " + c.getName());
        }
    }

    public static void requireNonNull(Object v) {
        if (null == v) {
            throw new ExecutionException("can not be null");
        }
    }

    public static void requireTrue(boolean value) {
        if (!value) {
            throw new DecodeException("must be true");
        }
    }

}
