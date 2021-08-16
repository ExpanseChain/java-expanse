package expanse.vm.wasm.instruction.memory;

import expanse.common.numeric.I32;
import expanse.common.numeric.I8;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.dump.DumpMemory;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.MemoryIndex;

public class I32Load8U implements LoadOperate {
    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);

        DumpMemory a = (DumpMemory) args;

        // System.err.println("So, which memory ?");
        byte[] bytes = mi.readBytes(MemoryIndex.of(I32.valueOf(0)), a, 1);

        mi.pushI32(I8.valueOf(new byte[]{ bytes[0] }).u32());
    }

}
