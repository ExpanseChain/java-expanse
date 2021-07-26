package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.model.Dump;

public class DataCountIndex extends I32 implements Dump {

    private DataCountIndex(I32 i32) {
        super(i32.bytes());
    }

    public static DataCountIndex of(I32 value) { return new DataCountIndex(value); }

    public String dump(int index) {
        return "DataCountIndex[" + index + "]: " + "value=" + unsigned();
    }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
