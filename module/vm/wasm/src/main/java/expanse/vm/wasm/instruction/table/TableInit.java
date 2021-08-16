package expanse.vm.wasm.instruction.table;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.dump.DumpTableInit;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.core.structure.ModuleInstance;

public class TableInit implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpTableInit(reader.readElementIndex(), reader.readTableIndex());
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpTableInit.class);

        DumpTableInit a = (DumpTableInit) args;

        Operate.super.operate(mi, args);
    }

}
