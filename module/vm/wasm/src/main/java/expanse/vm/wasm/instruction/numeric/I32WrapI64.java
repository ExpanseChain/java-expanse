package expanse.vm.wasm.instruction.numeric;

import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class I32WrapI64 implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        mi.pushI32(mi.popI64().i32());
    }
}
