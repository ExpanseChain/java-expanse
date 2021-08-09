package might.vm.wasm.instruction.numeric.i64.operate;

import might.common.numeric.I64;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;

public class I64Popcnt implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I64 v = mi.popI64();
        mi.pushI64(I64.valueOf(v.popcnt()));
    }

}