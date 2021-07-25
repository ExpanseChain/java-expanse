package wasm.core.instruction.reference;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.structure.WasmReader;

public class RefIsNull implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

}
