package might.vm.wasm.instruction.parametric;

import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.core2.numeric.USize;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.core.WasmReader;

public class Select implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        boolean v3 = mi.popBool();
        USize v2 = mi.popUSize();
        USize v1 = mi.popUSize();
        if (v3) {
            mi.pushUSize(v1);
        } else {
            mi.pushUSize(v2);
        }
    }

}