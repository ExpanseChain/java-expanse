package might.vm.wasm.model.index;

import might.common.numeric.I32;

public class FunctionIndex extends U32 {

    private FunctionIndex(I32 i32) {
        super(i32);
    }

    public static FunctionIndex of(I32 value) { return new FunctionIndex(value); }

    public String dump(int index) {
        return "FunctionIndex[" + index + "]: " + "value=" + unsigned();
    }

}
