package might.vm.wasm.core2.instruction.numeric.i32.compare;

import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.core2.numeric.U32;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class I32LtU implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U32 v2 = mi.popU32();
        U32 v1 = mi.popU32();
        mi.pushBool(v1.compareTo(v2) < 0);
    }

}
