package expanse.vm.wasm.instruction.numeric.i64.operate;

import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.execute.ExecutionException;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.util.NumberUtil;

public class I64DivS implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        long v2 = mi.popS64();
        long v1 = mi.popS64();
        if (v2 == 0) {
            throw new ExecutionException("divide 0 ?");
        }
        mi.pushS64(NumberUtil.divS(v1, v2));
    }

}
