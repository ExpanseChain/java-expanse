package might.vm.wasm.core2.instruction.reference;

import might.vm.wasm.core.error.Assertions;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.model.index.FunctionIndex;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

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
