package might.vm.wasm.instruction.dump;

import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.LabelIndex;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DumpBrTable implements Dump {

    public final LabelIndex[] labelIndices;

    public final LabelIndex omit;

    public DumpBrTable(LabelIndex[] labelIndices, LabelIndex omit) {
        this.labelIndices = labelIndices;
        this.omit = omit;
    }

    @Override
    public String dump() {
        return "[" + Stream.of(labelIndices).map(i -> i.unsigned().toString()).collect(Collectors.joining(",")) + "] " + omit;
    }
}