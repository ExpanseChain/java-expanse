package might.vm.wasm.instruction.table;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.instruction.dump.DumpTableInit;
import might.vm.wasm.model.Dump;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.core.WasmReader;

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
