package expanse.vm.wasm.model.index;

import expanse.common.numeric.I32;

public class DataIndex extends U32 {

    private DataIndex(I32 i32) {
        super(i32);
    }

    public static DataIndex of(I32 value) { return new DataIndex(value); }

    public String dump(int index) {
        return "data[" + index + "]: " + "value=" + unsigned();
    }

}
