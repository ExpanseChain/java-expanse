package might.vm.wasm.instruction.parametric;

import might.common.numeric.ISize;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;

public class Select implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        boolean v3 = mi.popBool();
        ISize v2 = mi.popISize();
        ISize v1 = mi.popISize();
        if (v3) {
            mi.pushISize(v1);
        } else {
            mi.pushISize(v2);
        }
    }

}
