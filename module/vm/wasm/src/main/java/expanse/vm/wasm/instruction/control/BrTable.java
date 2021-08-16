package expanse.vm.wasm.instruction.control;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.Instruction;
import expanse.vm.wasm.instruction.dump.DumpBrTable;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.util.Slice;

public class BrTable implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpBrTable(reader.readLabelIndices(), reader.readLabelIndex());
    }

    @Override
    public void validate(ModuleInfo info, Dump args, int parameters, long locals) {
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
