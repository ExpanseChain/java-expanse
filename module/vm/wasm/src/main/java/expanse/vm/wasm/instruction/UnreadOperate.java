package expanse.vm.wasm.instruction;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.model.Dump;

public interface UnreadOperate extends Operate {

    @Override
    default Dump read(WasmReader reader) {
        return null;
    }

    @Override
    default void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null == args);
    }

}
