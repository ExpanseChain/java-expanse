package might.vm.wasm.core2.instruction.memory;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.model.index.MemoryIndex;
import wasm.core.numeric.U32;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;

public class MemoryGrow implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U32 grow = mi.popU32();
        // System.err.println("So, which memory ?");
        U32 old = mi.memoryGrow(MemoryIndex.of(0), grow);
        mi.pushU32(old);
    }
    
}
