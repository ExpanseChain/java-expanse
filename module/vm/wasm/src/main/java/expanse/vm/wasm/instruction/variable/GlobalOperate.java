package expanse.vm.wasm.instruction.variable;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.error.decode.DecodeException;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.GlobalIndex;

public interface GlobalOperate extends Operate {

    @Override
    default Dump read(WasmReader reader) {
        return reader.readGlobalIndex();
    }

    @Override
    default void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof GlobalIndex);

        GlobalIndex gi = (GlobalIndex) args;

        if (info.globalCount <= gi.unsigned().longValue()) {
            throw new DecodeException(String.format("global index(%d) exceeds the number of global(%d)", gi.unsigned().longValue(), info.globalCount));
        }
    }

}
