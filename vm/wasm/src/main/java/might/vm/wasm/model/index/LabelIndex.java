package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.model.Dump;

public class LabelIndex extends I32 implements Dump {

    protected LabelIndex(I32 i32) {
        super(i32.bytes());
    }

    public static LabelIndex of(I32 value) { return new LabelIndex(value); }

    public String dump(int index) {
        return "label[" + index + "]: " + "value=" + unsigned();
    }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
