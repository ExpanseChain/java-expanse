package expanse.vm.wasm.instruction.numeric.i64;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.dump.DumpI64;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.core.structure.ModuleInstance;

public class I64Const implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpI64(reader.readLeb128S64());
    }

    @Override
    public void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof DumpI64);
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpI64.class);

        DumpI64 a = (DumpI64) args;

        mi.pushS64(a.value);
    }

}
