package expanse.vm.wasm.instruction.memory;

import expanse.common.numeric.I32;
import expanse.common.numeric.I64;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.dump.DumpMemory;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.MemoryIndex;

public class I64Load implements LoadOperate {
    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);

        DumpMemory a = (DumpMemory) args;

        // System.err.println("So, which memory ?");
        byte[] bytes = mi.readBytes(MemoryIndex.of(I32.valueOf(0)), a, 8);

        mi.pushI64(I64.valueOf(new byte[] {
            bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]
        }));
    }

}