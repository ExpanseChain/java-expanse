package expanse.vm.wasm.instruction.numeric.i64.operate;

import expanse.common.numeric.I64;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.execute.ExecutionException;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.util.NumberUtil;

public class I64DivU implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I64 v2 = mi.popI64();
        I64 v1 = mi.popI64();
        if (v2.equals(I64.ZERO)) {
            throw new ExecutionException("divide 0 ?");
        }
        mi.pushI64(NumberUtil.divU(v1, v2));
    }

}
