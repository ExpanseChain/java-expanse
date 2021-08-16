package expanse.vm.wasm.instruction.numeric;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.model.Dump;

public class I64Extend32S implements Operate {
    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null == args);
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        byte[] bytes = mi.popI64().bytes();

        mi.pushS64(I32.valueOf(new byte[]{
            bytes[4], bytes[5], bytes[6], bytes[7]
        }).unsigned().longValue());
    }

}
