package expanse.vm.wasm.model.index;

import expanse.common.numeric.I32;

public class LocalIndex extends U32 {

    private LocalIndex(I32 i32) {
        super(i32);
    }

    public static LocalIndex of(I32 value) { return new LocalIndex(value); }

    public String dump(int index) {
        return "local[" + index + "]: " + "value=" + unsigned();
    }

}
