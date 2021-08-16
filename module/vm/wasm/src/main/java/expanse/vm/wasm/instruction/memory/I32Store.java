package expanse.vm.wasm.instruction.memory;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.dump.DumpMemory;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.MemoryIndex;

public class I32Store implements StoreOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);

        DumpMemory a = (DumpMemory) args;

        I32 v = mi.popI32();
        byte[] bytes = v.bytes();

        // System.err.println("So, which memory ?");
        mi.writeBytes(MemoryIndex.of(I32.valueOf(0)), a,
                new byte[] {bytes[3], bytes[2], bytes[1], bytes[0]});
    }

}
