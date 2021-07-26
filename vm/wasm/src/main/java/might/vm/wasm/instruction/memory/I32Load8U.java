package might.vm.wasm.instruction.memory;

import might.common.numeric.I32;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.instruction.dump.DumpMemory;
import might.vm.wasm.core2.numeric.U8;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.MemoryIndex;

public class I32Load8U implements Operate {

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

        mi.pushU32(U8.valueOf(new byte[]{ bytes[0] }).u32());
    }

}