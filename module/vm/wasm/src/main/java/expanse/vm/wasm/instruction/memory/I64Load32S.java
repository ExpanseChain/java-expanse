package expanse.vm.wasm.instruction.memory;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.dump.DumpMemory;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.MemoryIndex;

public class I64Load32S implements LoadOperate {
    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);

        DumpMemory a = (DumpMemory) args;

        // System.err.println("So, which memory ?");
        byte[] bytes = mi.readBytes(MemoryIndex.of(I32.valueOf(0)), a, 4);

        mi.pushS64(
            ((long) bytes[3] << 24) |
            ((bytes[2] << 16) & 0x00FF0000) |
            ((bytes[1] <<  8) & 0x0000FF00) |
            (bytes[0] & 0xFF)
        );
    }

}
