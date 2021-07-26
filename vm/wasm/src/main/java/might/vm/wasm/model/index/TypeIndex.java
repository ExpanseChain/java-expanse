package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.model.Dump;

public class TypeIndex extends I32 implements Dump {

    private TypeIndex(I32 i32) {
        super(i32.bytes());
    }

    public static TypeIndex of(I32 value) { return new TypeIndex(value); }

    public String dump(int index) {
        return "func[" + index + "]: " + "value=" + unsigned();
    }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
