package expanse.vm.wasm.instruction.numeric.i64.operate;

import expanse.common.numeric.I64;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class I64Ctz implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I64 v = mi.popI64();
        mi.pushI64(I64.valueOf(v.ctz()));
    }

}
