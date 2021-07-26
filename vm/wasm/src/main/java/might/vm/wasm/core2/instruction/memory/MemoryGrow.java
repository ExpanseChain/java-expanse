package might.vm.wasm.core2.instruction.memory;

import might.common.numeric.I32;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.MemoryIndex;
import might.vm.wasm.core2.numeric.U32;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class MemoryGrow implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U32 grow = mi.popU32();
        // System.err.println("So, which memory ?");
        U32 old = mi.memoryGrow(MemoryIndex.of(I32.valueOf(0)), grow);
        mi.pushU32(old);
    }
    
}
