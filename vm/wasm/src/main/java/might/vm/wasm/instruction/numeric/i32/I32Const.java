package might.vm.wasm.instruction.numeric.i32;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.instruction.dump.DumpI32;
import might.vm.wasm.model.Dump;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.core.WasmReader;

public class I32Const implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpI32(reader.readLeb128S32());
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpI32.class);

        DumpI32 a = (DumpI32) args;

        mi.pushS32(a.value);
    }

}
