package expanse.vm.wasm.instruction.reference;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.FunctionIndex;
import expanse.vm.wasm.core.structure.ModuleInstance;

public class RefFunc implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readFunctionIndex();
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, FunctionIndex.class);

        FunctionIndex a = (FunctionIndex) args;

        Operate.super.operate(mi, args);
    }
}
