package might.vm.wasm.core2.instruction.numeric.i64.operate;

import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.numeric.U64;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;
import might.vm.wasm.util.NumberUtil;

public class I64Rotr implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U64 v2 = mi.popU64();
        U64 v1 = mi.popU64();
        mi.pushU64(NumberUtil.rotr(v1, v2));
    }

}
