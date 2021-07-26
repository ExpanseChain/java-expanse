package might.vm.wasm.core2.model.index;

import might.vm.wasm.core2.numeric.U32;

public class LabelIndex extends U32 {

    protected LabelIndex(U32 u32) {
        super(u32);
    }

    public static LabelIndex of(U32 value) { return new LabelIndex(value); }

    public String dump(int index) {
        return "label[" + index + "]: " + "value=" + super.toString();
    }

}
