package might.vm.wasm.instruction.variable;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.error.decode.DecodeException;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.LocalIndex;
import might.vm.wasm.util.Slice;

public interface LocalOperate extends Operate {

    @Override
    default Dump read(WasmReader reader) {
        return reader.readLocalIndex();
    }

    @Override
    default void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof LocalIndex);
        Assertions.requireTrue(0 <= parameters);
        Assertions.requireTrue(0 <= locals);

        int i = ((LocalIndex) args).unsigned().intValue();
        Slice.checkArrayIndex(i);
        long max = parameters + locals;
        if (max <= i) {
            throw new DecodeException(String.format("try get local variable by index: %d  but local number: %d", i, max));
        }
    }

}
