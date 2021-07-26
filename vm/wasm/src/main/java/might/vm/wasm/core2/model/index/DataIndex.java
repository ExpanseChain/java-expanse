package might.vm.wasm.core2.model.index;

import might.vm.wasm.core2.numeric.U32;

public class DataIndex extends U32 {

    private DataIndex(U32 u32) {
        super(u32);
    }

    public static DataIndex of(U32 value) { return new DataIndex(value); }

    public String dump(int index) {
        return "data[" + index + "]: " + "value=" + super.toString();
    }

}
