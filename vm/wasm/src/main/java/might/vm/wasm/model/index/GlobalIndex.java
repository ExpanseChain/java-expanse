package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.model.Dump;

public class GlobalIndex extends I32 implements Dump {

    private GlobalIndex(I32 i32) {
        super(i32.bytes());
    }

    public static GlobalIndex of(I32 value) { return new GlobalIndex(value); }

    public String dump(int index) {
        return "global[" + index + "]: " + "value=" + unsigned();
    }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
