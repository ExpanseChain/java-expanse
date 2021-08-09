package might.vm.wasm.instruction.variable;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.error.decode.DecodeException;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.GlobalIndex;

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
