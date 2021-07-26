package might.vm.wasm.instruction.numeric;

import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.core2.numeric.U64;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.core.WasmReader;

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
