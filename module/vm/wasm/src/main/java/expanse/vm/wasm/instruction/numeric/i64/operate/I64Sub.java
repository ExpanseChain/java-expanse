package expanse.vm.wasm.instruction.numeric.i64.operate;

import expanse.common.numeric.I64;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class I64Sub implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I64 v2 = mi.popI64();
        I64 v1 = mi.popI64();
        mi.pushI64(v1.sub(v2));
    }

}
