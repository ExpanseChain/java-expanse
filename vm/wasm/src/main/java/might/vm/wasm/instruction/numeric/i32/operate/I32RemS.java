package might.vm.wasm.instruction.numeric.i32.operate;

import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.util.NumberUtil;

public class I32RemS implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        int v2 = mi.popS32();
        int v1 = mi.popS32();
        mi.pushS32(NumberUtil.remS(v1, v2));
    }

}
