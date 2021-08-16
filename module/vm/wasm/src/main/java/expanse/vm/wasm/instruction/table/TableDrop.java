package expanse.vm.wasm.instruction.table;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.ElementIndex;
import expanse.vm.wasm.core.structure.ModuleInstance;

public class TableDrop implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readElementIndex();
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, ElementIndex.class);

        ElementIndex a = (ElementIndex) args;

        Operate.super.operate(mi, args);
    }

}
