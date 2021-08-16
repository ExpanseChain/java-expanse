package expanse.vm.wasm.instruction.numeric.i32;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.dump.DumpI32;
import expanse.vm.wasm.model.Dump;

public class I32Const implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpI32(reader.readLeb128S32());
    }

    @Override
    public void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof DumpI32);
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpI32.class);

        DumpI32 a = (DumpI32) args;

        mi.pushS32(a.value);
    }

}
