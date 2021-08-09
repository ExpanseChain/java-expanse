package might.vm.wasm.instruction.numeric.i32.operate;

import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.execute.ExecutionException;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.util.NumberUtil;

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
