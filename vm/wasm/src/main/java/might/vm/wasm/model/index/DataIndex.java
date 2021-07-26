package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.model.Dump;

public class DataIndex extends I32 implements Dump {

    private DataIndex(I32 i32) {
        super(i32.bytes());
    }

    public static DataIndex of(I32 value) { return new DataIndex(value); }

    public String dump(int index) {
        return "data[" + index + "]: " + "value=" + unsigned();
    }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
