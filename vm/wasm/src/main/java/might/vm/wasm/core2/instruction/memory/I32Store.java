package might.vm.wasm.core2.instruction.memory;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.instruction.dump.DumpMemory;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.MemoryIndex;
import might.vm.wasm.core2.numeric.U32;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class I32Store implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpMemory(reader.readLeb128U32(), reader.readLeb128U32());
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);

        DumpMemory a = (DumpMemory) args;

        U32 v = mi.popU32();
        byte[] bytes = v.getBytes();

        // System.err.println("So, which memory ?");
        mi.writeBytes(MemoryIndex.of(U32.valueOf(0)), a,
                new byte[] {bytes[3], bytes[2], bytes[1], bytes[0]});
    }

}
