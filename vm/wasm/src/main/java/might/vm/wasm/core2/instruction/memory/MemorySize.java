package might.vm.wasm.core2.instruction.memory;

import might.common.numeric.I32;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.MemoryIndex;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class MemorySize implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        // System.err.println("The memory.size instruction returns the current size of a memory. So, which memory ?");
        mi.pushU32(mi.memorySize(MemoryIndex.of(I32.valueOf(0))));
    }

}
