package might.vm.wasm.core2.instruction.memory;

import might.vm.wasm.core.error.Assertions;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.instruction.dump.DumpMemory;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.model.index.MemoryIndex;
import might.vm.wasm.core2.numeric.U32;
import might.vm.wasm.core2.numeric.U64;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class I64Store32 implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpMemory(reader.readLeb128U32(), reader.readLeb128U32());
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);

        DumpMemory a = (DumpMemory) args;

        U64 v = mi.popU64();
        byte[] bytes = v.getBytes();

        // System.err.println("So, which memory ?");
        mi.writeBytes(MemoryIndex.of(U32.valueOf(0)), a,
                new byte[] {bytes[7], bytes[6], bytes[5], bytes[4]});
    }

}
