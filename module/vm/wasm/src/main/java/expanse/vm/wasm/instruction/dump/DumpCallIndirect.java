package expanse.vm.wasm.instruction.dump;

import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.TableIndex;
import expanse.vm.wasm.model.index.TypeIndex;

public class DumpCallIndirect implements Dump {

    public final TypeIndex typeIndex;

    public final TableIndex tableIndex;

    public DumpCallIndirect(TypeIndex typeIndex, TableIndex tableIndex) {
        this.typeIndex = typeIndex;
        this.tableIndex = tableIndex;
    }

    @Override
    public String dump() {
        return "typeidx: " +  typeIndex.unsigned() + "  tableidx: " + tableIndex.unsigned();
    }

}
