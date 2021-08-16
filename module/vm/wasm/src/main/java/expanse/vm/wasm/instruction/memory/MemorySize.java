package expanse.vm.wasm.instruction.memory;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.MemoryIndex;

public class MemorySize implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        // System.err.println("The memory.size instruction returns the current size of a memory. So, which memory ?");
        mi.pushI32(mi.memorySize(MemoryIndex.of(I32.valueOf(0))));
    }

}
