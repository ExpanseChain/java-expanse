package might.vm.wasm.instruction.numeric;

import might.common.numeric.I8;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;

public class I32Extend8S implements Operate {
    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void valid(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null == args);
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        byte[] bytes = mi.popI32().bytes();

        mi.pushS32(I8.valueOf(new byte[]{
            bytes[3]
        }).s32().signed().intValue());
    }

}
