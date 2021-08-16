package might.vm.wasm.model.index;

import expanse.common.numeric.I32;

public class LabelIndex extends U32 {

    private LabelIndex(I32 i32) {
        super(i32);
    }

    public static LabelIndex of(I32 value) { return new LabelIndex(value); }

    public String dump(int index) {
        return "label[" + index + "]: " + "value=" + unsigned();
    }

}
