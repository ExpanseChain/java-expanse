package expanse.vm.wasm.model.index;

import expanse.common.numeric.I32;

public class GlobalIndex extends U32 {

    private GlobalIndex(I32 i32) {
        super(i32);
    }

    public static GlobalIndex of(I32 value) { return new GlobalIndex(value); }

    public String dump(int index) {
        return "global[" + index + "]: " + "value=" + unsigned();
    }

}
