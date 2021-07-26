package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.model.Dump;

public class LocalIndex extends I32 implements Dump {

    protected LocalIndex(I32 i32) {
        super(i32.bytes());
    }

    public static LocalIndex of(I32 value) { return new LocalIndex(value); }

    public String dump(int index) {
        return "local[" + index + "]: " + "value=" + unsigned();
    }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
