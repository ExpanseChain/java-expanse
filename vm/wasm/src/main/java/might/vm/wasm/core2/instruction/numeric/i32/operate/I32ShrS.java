package might.vm.wasm.core2.instruction.numeric.i32.operate;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.numeric.U32;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;
import wasm.core.util.NumberUtil;

public class I32ShrS implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U32 v2 = mi.popU32();
        U32 v1 = mi.popU32();
        mi.pushU32(NumberUtil.shrS(v1, v2));
    }

}
