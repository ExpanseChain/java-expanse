package might.vm.wasm.core2.instruction.table;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.instruction.dump.DumpTableInit;
import might.vm.wasm.model.Dump;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

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
