package might.vm.wasm.instruction.numeric.i64.compare;

import might.common.numeric.I64;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;

public class I64LeU implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I64 v2 = mi.popI64();
        I64 v1 = mi.popI64();
        mi.pushBool(v1.lessOrEqualsU(v2));
    }

}