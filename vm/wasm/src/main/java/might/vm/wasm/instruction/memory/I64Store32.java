package might.vm.wasm.instruction.memory;

import might.common.numeric.I32;
import might.common.numeric.I64;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.dump.DumpMemory;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.MemoryIndex;

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
