package expanse.vm.wasm.instruction.numeric.i32.operate;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.execute.ExecutionException;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.util.NumberUtil;

public class I32RemU implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I32 v2 = mi.popI32();
        I32 v1 = mi.popI32();
        if (v2.equals(I32.ZERO)) {
            throw new ExecutionException("rem 0 ?");
        }
        mi.pushI32(NumberUtil.remU(v1, v2));
    }

}
