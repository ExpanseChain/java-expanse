package might.vm.wasm.core2.instruction.numeric.i32.compare;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.numeric.U32;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;

public class I32LeU implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U32 v2 = mi.popU32();
        U32 v1 = mi.popU32();
        mi.pushBool(v1.compareTo(v2) <= 0);
    }

}
