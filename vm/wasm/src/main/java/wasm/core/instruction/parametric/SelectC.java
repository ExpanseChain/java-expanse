package wasm.core.instruction.parametric;

import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.model.type.Types;
import wasm.core.structure.WasmReader;

public class SelectC implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new Types(reader.readValueTypes());
    }

}
