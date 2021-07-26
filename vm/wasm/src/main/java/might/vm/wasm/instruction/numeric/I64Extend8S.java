package might.vm.wasm.instruction.numeric;

import might.common.numeric.I8;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;

public class I64Extend8S implements Operate {
    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        byte[] bytes = mi.popI64().bytes();

        mi.pushS64(I8.valueOf(new byte[]{
            bytes[7]
        }).s64().signed().longValue());
    }

}
