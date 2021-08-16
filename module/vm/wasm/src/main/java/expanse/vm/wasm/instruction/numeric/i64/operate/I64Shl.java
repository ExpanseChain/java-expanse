package expanse.vm.wasm.instruction.numeric.i64.operate;

import expanse.common.numeric.I64;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.util.NumberUtil;

public class I64Shl implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I64 v2 = mi.popI64();
        I64 v1 = mi.popI64();
        mi.pushI64(NumberUtil.shl(v1, v2));
    }

}
