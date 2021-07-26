package might.vm.wasm.model.index;

import might.common.numeric.I32;
import might.vm.wasm.model.Dump;

class U32 extends I32 implements Dump {

    public U32(I32 i32) { super(i32.bytes()); }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
