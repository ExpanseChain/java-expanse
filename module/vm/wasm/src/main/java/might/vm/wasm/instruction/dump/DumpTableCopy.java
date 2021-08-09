package might.vm.wasm.instruction.dump;

import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.TableIndex;

public class DumpTableCopy implements Dump {

    private final TableIndex tableIndex1;

    private final TableIndex tableIndex2;

    public DumpTableCopy(TableIndex tableIndex1, TableIndex tableIndex2) {
        this.tableIndex1 = tableIndex1;
        this.tableIndex2 = tableIndex2;
    }

    @Override
    public String dump() {
        return tableIndex1.unsigned() + " " + tableIndex2.unsigned();
    }

}
