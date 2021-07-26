package might.vm.wasm.instruction.table;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.ElementIndex;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.core.WasmReader;

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
