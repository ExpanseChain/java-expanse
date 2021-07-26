package might.vm.wasm.instruction;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.model.Dump;

public interface UnreadOperate extends Operate {

    @Override
    default Dump read(WasmReader reader) {
        return null;
    }

    @Override
    default void valid(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null == args);
    }

}
