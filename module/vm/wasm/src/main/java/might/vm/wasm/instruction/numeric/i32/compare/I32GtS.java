package might.vm.wasm.instruction.numeric.i32.compare;

import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;

public class I32GtS implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        int v2 = mi.popS32();
        int v1 = mi.popS32();
        mi.pushBool(v1 > v2);
    }

}
