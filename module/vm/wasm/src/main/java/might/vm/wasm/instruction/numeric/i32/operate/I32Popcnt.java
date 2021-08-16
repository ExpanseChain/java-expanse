package might.vm.wasm.instruction.numeric.i32.operate;

import expanse.common.numeric.I32;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;

public class I32Popcnt implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I32 v = mi.popI32();
        mi.pushI32(I32.valueOf(v.popcnt()));
    }

}
