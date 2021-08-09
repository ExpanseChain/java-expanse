package might.vm.wasm.instruction.reference;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.FunctionIndex;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.core.WasmReader;

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
