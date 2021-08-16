package expanse.vm.wasm.instruction.reference;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.type.ReferenceType;
import expanse.vm.wasm.core.structure.ModuleInstance;

public class RefNull implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return ReferenceType.of(reader.readByte());
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, ReferenceType.class);

        ReferenceType a = (ReferenceType) args;

        Operate.super.operate(mi, args);
    }

}
