package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.model.Dump;

public class TableIndex extends I32 implements Dump {

    private TableIndex(I32 i32) {
        super(i32.bytes());
    }

    public static TableIndex of(I32 value) { return new TableIndex(value); }

    public String dump(int index) {
        return "table[" + index + "]: " + "value=" + unsigned();
    }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
