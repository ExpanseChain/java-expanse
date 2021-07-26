package might.vm.wasm.instruction.memory;

import might.common.numeric.I32;
import might.common.numeric.I8;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.dump.DumpMemory;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.MemoryIndex;

public class I64Load8U implements LoadOperate {
    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);

        DumpMemory a = (DumpMemory) args;

        // System.err.println("So, which memory ?");
        byte[] bytes = mi.readBytes(MemoryIndex.of(I32.valueOf(0)), a, 1);

        mi.pushI64(I8.valueOf(new byte[]{ bytes[0] }).u64());
    }

}
