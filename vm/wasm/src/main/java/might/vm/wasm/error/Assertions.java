package might.vm.wasm.error;

import might.vm.wasm.error.assertion.AssertionTrueException;
import might.vm.wasm.error.assertion.AssertionTypeException;

public class Assertions {

    public static void requireType(Object o, Class<?> c) {
        if (o.getClass() != c) {
            throw new AssertionTypeException("o is not " + c.getName());
        }
    }

    public static void requireNonNull(Object v) {
        if (null == v) {
            throw new NullPointerException("can not be null");
        }
    }

    public static void requireTrue(boolean value) {
        if (!value) {
            throw new AssertionTrueException("must be true");
        }
    }

}
