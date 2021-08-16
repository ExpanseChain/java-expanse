package expanse.vm.wasm.instruction.numeric.i32.operate;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.model.Dump;

public class I32Sub implements Operate {

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
        I32 v2 = mi.popI32();
        I32 v1 = mi.popI32();
        mi.pushI32(v1.sub(v2));
    }

}
