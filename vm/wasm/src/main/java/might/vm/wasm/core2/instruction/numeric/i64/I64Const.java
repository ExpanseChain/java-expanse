package might.vm.wasm.core2.instruction.numeric.i64;

import might.vm.wasm.core.error.Assertions;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.instruction.dump.DumpI64;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class I64Const implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpI64(reader.readLeb128S64());
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpI64.class);

        DumpI64 a = (DumpI64) args;

        mi.pushS64(a.value);
    }

}
