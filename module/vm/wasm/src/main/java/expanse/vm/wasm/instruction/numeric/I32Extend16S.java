package expanse.vm.wasm.instruction.numeric;

import expanse.common.numeric.I16;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class I32Extend16S implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        byte[] bytes = mi.popI32().bytes();

        mi.pushS32(I16.valueOf(new byte[]{
            bytes[2], bytes[3]
        }).s32().signed().intValue());
    }

}
