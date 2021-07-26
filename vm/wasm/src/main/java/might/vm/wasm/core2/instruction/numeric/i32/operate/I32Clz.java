package might.vm.wasm.core2.instruction.numeric.i32.operate;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.numeric.U32;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;

public class I32Clz implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U32 v = mi.popU32();
        mi.pushU32(U32.valueOf(v.clz()));
    }

}
