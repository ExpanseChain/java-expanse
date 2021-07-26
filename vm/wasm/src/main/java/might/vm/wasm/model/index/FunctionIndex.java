package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.model.Dump;

public class FunctionIndex extends I32 implements Dump {

    private FunctionIndex(I32 i32) {
        super(i32.bytes());
    }

    public static FunctionIndex of(I32 value) { return new FunctionIndex(value); }

    public String dump(int index) {
        return "FunctionIndex[" + index + "]: " + "value=" + unsigned();
    }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
