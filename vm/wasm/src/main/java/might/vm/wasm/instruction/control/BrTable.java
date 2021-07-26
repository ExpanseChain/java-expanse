package might.vm.wasm.instruction.control;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.instruction.dump.DumpBrTable;
import might.vm.wasm.model.Dump;
import might.vm.wasm.util.Slice;

public class BrTable implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpBrTable(reader.readLabelIndices(), reader.readLabelIndex());
    }

    @Override
    public void valid(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof DumpBrTable);
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpBrTable.class);

        DumpBrTable t = (DumpBrTable) args;

        int n = mi.popI32().signed().intValue();

        if (n < t.labelIndices.length) {
            Instruction.BR.operate(mi, t.labelIndices[Slice.checkArrayIndex(n)]);
        } else {
            Instruction.BR.operate(mi, t.omit);
        }

    }
}
