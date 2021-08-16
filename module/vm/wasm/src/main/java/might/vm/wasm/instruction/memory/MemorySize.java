package might.vm.wasm.instruction.memory;

import expanse.common.numeric.I32;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.MemoryIndex;

public class MemorySize implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        // System.err.println("The memory.size instruction returns the current size of a memory. So, which memory ?");
        mi.pushI32(mi.memorySize(MemoryIndex.of(I32.valueOf(0))));
    }

}
