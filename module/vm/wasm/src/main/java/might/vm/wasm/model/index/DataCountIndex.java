package might.vm.wasm.model.index;

import expanse.common.numeric.I32;

public class DataCountIndex extends U32 {

    private DataCountIndex(I32 i32) {
        super(i32);
    }

    public static DataCountIndex of(I32 value) { return new DataCountIndex(value); }

    public String dump(int index) {
        return "DataCountIndex[" + index + "]: " + "value=" + unsigned();
    }

}
