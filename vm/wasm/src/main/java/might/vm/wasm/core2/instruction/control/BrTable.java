package might.vm.wasm.core2.instruction.control;

import might.vm.wasm.core.error.Assertions;
import might.vm.wasm.core2.instruction.Instruction;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.instruction.dump.DumpBrTable;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class BrTable implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpBrTable(reader.readLabelIndices(), reader.readLabelIndex());
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpBrTable.class);

        DumpBrTable t = (DumpBrTable) args;

        int n = mi.popU32().intValue();

        if (n < t.labelIndices.length) {
            Instruction.BR.operate(mi, t.labelIndices[n]);
        } else {
            Instruction.BR.operate(mi, t.omit);
        }

    }
}
