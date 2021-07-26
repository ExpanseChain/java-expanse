package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.model.Dump;

public class ElementIndex extends I32 implements Dump {
    
    private ElementIndex(I32 i32) {
        super(i32.bytes());
    }

    public static ElementIndex of(I32 value) { return new ElementIndex(value); }

    public String dump(int index) {
        return "elem[" + index + "]: " + "value=" + unsigned();
    }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
