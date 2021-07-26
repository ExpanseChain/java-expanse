package might.vm.wasm.core2.instruction.table;

import might.vm.wasm.core.error.Assertions;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.model.index.TableIndex;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class TableSet implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readTableIndex();
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, TableIndex.class);

        TableIndex a = (TableIndex) args;

        Operate.super.operate(mi, args);
    }

}
