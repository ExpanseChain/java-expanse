package might.vm.wasm.instruction.numeric.i64.compare;

import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.core2.numeric.U64;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.core.WasmReader;

public class I64Eq implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U64 v2 = mi.popU64();
        U64 v1 = mi.popU64();
        mi.pushBool(v1.equals(v2));
    }

}