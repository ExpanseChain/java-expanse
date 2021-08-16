package expanse.vm.wasm.instruction.parametric;

import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.type.Types;
import expanse.vm.wasm.core.WasmReader;

public class SelectC implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new Types(reader.readValueTypes());
    }

}
