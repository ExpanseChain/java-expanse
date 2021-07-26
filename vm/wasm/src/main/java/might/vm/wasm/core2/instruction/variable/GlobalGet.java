package might.vm.wasm.core2.instruction.variable;

import might.vm.wasm.core.error.Assertions;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.model.index.GlobalIndex;
import might.vm.wasm.core2.numeric.USize;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class GlobalGet implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readGlobalIndex();
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, GlobalIndex.class);

        GlobalIndex a = (GlobalIndex) args;

        USize value = mi.getGlobal(a).get();

        mi.pushUSize(value);
    }

}