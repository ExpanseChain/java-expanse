package expanse.vm.wasm.instruction.numeric.i32.operate;

import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.execute.ExecutionException;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.util.NumberUtil;

public class I32DivS implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        int v2 = mi.popS32();
        int v1 = mi.popS32();
        if (v2 == 0) {
            throw new ExecutionException("divide 0 ?");
        }
        mi.pushS32(NumberUtil.divS(v1, v2));
    }

}
