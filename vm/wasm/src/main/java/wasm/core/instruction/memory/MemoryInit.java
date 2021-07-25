package wasm.core.instruction.memory;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.structure.WasmReader;

public class MemoryInit implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readDataIndex();
    }

}
