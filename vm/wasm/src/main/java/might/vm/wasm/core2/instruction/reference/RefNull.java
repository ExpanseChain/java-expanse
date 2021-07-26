package might.vm.wasm.core2.instruction.reference;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.type.ReferenceType;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

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
