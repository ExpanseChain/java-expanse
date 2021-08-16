package expanse.vm.wasm.model.index;

import expanse.common.numeric.I32;

public class TableIndex extends U32 {

    private TableIndex(I32 i32) {
        super(i32);
    }

    public static TableIndex of(I32 value) { return new TableIndex(value); }

    public String dump(int index) {
        return "table[" + index + "]: " + "value=" + unsigned();
    }

}
