package wasm.core.instruction.numeric.i64.operate;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.numeric.U64;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;
import wasm.core.util.NumberUtil;

public class I64RemU implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U64 v2 = mi.popU64();
        U64 v1 = mi.popU64();
        mi.pushU64(NumberUtil.remU(v1, v2));
    }

}
