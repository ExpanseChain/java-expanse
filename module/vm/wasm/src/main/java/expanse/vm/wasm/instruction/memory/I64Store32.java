package expanse.vm.wasm.instruction.memory;

import expanse.common.numeric.I32;
import expanse.common.numeric.I64;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.dump.DumpMemory;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.MemoryIndex;

public class I64Store32 implements StoreOperate {
    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);

        DumpMemory a = (DumpMemory) args;

        I64 v = mi.popI64();
        byte[] bytes = v.bytes();

        // System.err.println("So, which memory ?");
        mi.writeBytes(MemoryIndex.of(I32.valueOf(0)), a,
                new byte[] {bytes[7], bytes[6], bytes[5], bytes[4]});
    }

}
