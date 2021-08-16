package might.vm.wasm.instruction.numeric.i64.operate;

import expanse.common.numeric.I64;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.execute.ExecutionException;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.util.NumberUtil;

public class I64RemU implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I64 v2 = mi.popI64();
        I64 v1 = mi.popI64();
        if (v2.equals(I64.ZERO)) {
            throw new ExecutionException("rem 0 ?");
        }
        mi.pushI64(NumberUtil.remU(v1, v2));
    }

}
