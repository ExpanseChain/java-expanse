package might.vm.wasm.core2.instruction.numeric;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.numeric.U32;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;

public class I32Extend8S implements Operate {
    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        byte[] bytes = mi.popU32().getBytes();

        mi.pushS32(U32.valueOfS(new byte[]{
            bytes[3]
        }).intValue());
    }

}
