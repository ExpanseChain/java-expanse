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

public class I64Load8U implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpMemory(reader.readLeb128U32(), reader.readLeb128U32());
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);

        DumpMemory a = (DumpMemory) args;

        // System.err.println("So, which memory ?");
        byte[] bytes = mi.readBytes(MemoryIndex.of(I32.valueOf(0)), a, 1);

        mi.pushU64(U64.valueOfU(new byte[]{ bytes[0] }));
    }

}
