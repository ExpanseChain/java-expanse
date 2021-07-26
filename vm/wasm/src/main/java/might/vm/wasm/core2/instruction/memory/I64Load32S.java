package might.vm.wasm.core2.instruction.memory;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.instruction.dump.DumpMemory;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.MemoryIndex;
import might.vm.wasm.core2.numeric.U32;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class I64Load32S implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpMemory(reader.readLeb128U32(), reader.readLeb128U32());
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);

        DumpMemory a = (DumpMemory) args;

        // System.err.println("So, which memory ?");
        byte[] bytes = mi.readBytes(MemoryIndex.of(U32.valueOf(0)), a, 4);

        mi.pushS64(
            ((long) bytes[3] << 24) |
            ((bytes[2] << 16) & 0x00FF0000) |
            ((bytes[1] <<  8) & 0x0000FF00) |
            (bytes[0] & 0xFF)
        );
    }

}
