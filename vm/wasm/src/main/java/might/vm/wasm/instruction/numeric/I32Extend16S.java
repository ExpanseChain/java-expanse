package might.vm.wasm.instruction.numeric;

import might.common.numeric.I16;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;

public class I32Extend16S implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        byte[] bytes = mi.popI32().bytes();

        mi.pushS32(I16.valueOf(new byte[]{
            bytes[2], bytes[3]
        }).s32().signed().intValue());
    }

}
