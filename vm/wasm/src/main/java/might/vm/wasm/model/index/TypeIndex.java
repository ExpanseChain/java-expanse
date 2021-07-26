package might.vm.wasm.model.index;

import might.common.numeric.I32;

public class TypeIndex extends U32 {

    private TypeIndex(I32 i32) {
        super(i32);
    }

    public static TypeIndex of(I32 value) { return new TypeIndex(value); }

    public String dump(int index) {
        return "func[" + index + "]: " + "value=" + unsigned();
    }

}
