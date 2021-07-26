package might.vm.wasm.core2.instruction.numeric.i64.operate;

import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.numeric.U64;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class I64Popcnt implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U64 v = mi.popU64();
        mi.pushU64(U64.valueOf(v.popcnt()));
    }

}
