package might.vm.wasm.instruction.numeric.i32.operate;

import might.common.numeric.I32;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.util.NumberUtil;

public class I32RemU implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I32 v2 = mi.popI32();
        I32 v1 = mi.popI32();
        mi.pushI32(NumberUtil.remU(v1, v2));
    }

}
