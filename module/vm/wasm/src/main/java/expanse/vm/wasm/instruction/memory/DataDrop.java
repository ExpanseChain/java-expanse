package expanse.vm.wasm.instruction.memory;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.DataIndex;

public class DataDrop implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readDataIndex();
    }

    @Override
    public void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof DataIndex);
    }

}
