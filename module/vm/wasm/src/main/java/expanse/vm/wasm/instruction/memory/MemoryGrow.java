package expanse.vm.wasm.instruction.memory;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.MemoryIndex;

public class MemoryGrow implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I32 grow = mi.popI32();
        // System.err.println("So, which memory ?");
        I32 old = mi.memoryGrow(MemoryIndex.of(I32.valueOf(0)), grow);
        mi.pushI32(old);
    }
    
}
