package might.vm.wasm.instruction.memory;

import might.common.numeric.I32;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.MemoryIndex;

public class MemoryGrow implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        I32 grow = mi.popI32();
        // System.err.println("So, which memory ?");
        I32 old = mi.memoryGrow(MemoryIndex.of(I32.valueOf(0)), grow);
        mi.pushI32(old);
    }
    
}
