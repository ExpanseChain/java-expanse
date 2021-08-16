package expanse.vm.wasm.instruction.dump;

import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.ElementIndex;
import expanse.vm.wasm.model.index.TableIndex;

public class DumpTableInit implements Dump {

    private final ElementIndex elementIndex;

    private final TableIndex tableIndex;

    public DumpTableInit(ElementIndex elementIndex, TableIndex tableIndex) {
        this.elementIndex = elementIndex;
        this.tableIndex = tableIndex;
    }

    @Override
    public String dump() {
        return elementIndex.unsigned() + " " + tableIndex.unsigned();
    }

}
