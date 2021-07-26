package might.vm.wasm.core2.instruction.numeric.i32.operate;

import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.numeric.U32;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;
import might.vm.wasm.util.NumberUtil;

public class I32RemU implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U32 v2 = mi.popU32();
        U32 v1 = mi.popU32();
        mi.pushU32(NumberUtil.remU(v1, v2));
    }

}
