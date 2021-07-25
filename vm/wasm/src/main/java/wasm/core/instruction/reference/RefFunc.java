package wasm.core.instruction.reference;

import wasm.core.exception.Check;
import wasm.core.instruction.Operate;
import wasm.core.model.Dump;
import wasm.core.model.index.FunctionIndex;
import wasm.core.structure.ModuleInstance;
import wasm.core.structure.WasmReader;

public class RefFunc implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readFunctionIndex();
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Check.requireNonNull(args);
        Check.require(args, FunctionIndex.class);

        FunctionIndex a = (FunctionIndex) args;

        Operate.super.operate(mi, args);
    }
}
