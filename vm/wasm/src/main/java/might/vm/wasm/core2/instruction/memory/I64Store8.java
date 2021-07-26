package might.vm.wasm.core2.instruction.memory;

import might.common.numeric.I32;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.instruction.dump.DumpMemory;
import might.vm.wasm.core2.numeric.U64;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.MemoryIndex;

public class I64Store8 implements Operate {

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
        mi.writeBytes(MemoryIndex.of(I32.valueOf(0)), a,
                new byte[] {bytes[7]});
    }

}
