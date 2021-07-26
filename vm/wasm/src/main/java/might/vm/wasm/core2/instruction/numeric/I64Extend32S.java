package might.vm.wasm.core2.instruction.numeric;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.numeric.U64;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;

public class I64Extend32S implements Operate {
    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        byte[] bytes = mi.popU64().getBytes();

        mi.pushS64(U64.valueOfS(new byte[]{
            bytes[4], bytes[5], bytes[6], bytes[7]
        }).longValue());
    }

}
