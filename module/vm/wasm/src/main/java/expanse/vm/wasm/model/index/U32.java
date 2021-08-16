package expanse.vm.wasm.model.index;

import expanse.common.numeric.I32;
import expanse.vm.wasm.model.Dump;

class U32 extends I32 implements Dump {

    public U32(I32 i32) { super(i32.bytes()); }

    @Override
    public String dump() {
        return unsigned().toString();
    }

}
