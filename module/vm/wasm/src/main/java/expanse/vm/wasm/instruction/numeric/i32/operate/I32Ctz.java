package expanse.vm.wasm.instruction.numeric.i32.operate;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class I32Ctz implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I32 v = mi.popI32();
        mi.pushI32(I32.valueOf(v.ctz()));
    }

}
