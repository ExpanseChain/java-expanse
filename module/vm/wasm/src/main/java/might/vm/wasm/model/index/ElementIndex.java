package might.vm.wasm.model.index;

import might.common.numeric.I32;

public class ElementIndex extends U32 {
    
    private ElementIndex(I32 i32) {
        super(i32);
    }

    public static ElementIndex of(I32 value) { return new ElementIndex(value); }

    public String dump(int index) {
        return "elem[" + index + "]: " + "value=" + unsigned();
    }

}
