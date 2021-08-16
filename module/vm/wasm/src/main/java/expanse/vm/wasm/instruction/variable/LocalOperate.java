package expanse.vm.wasm.instruction.variable;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.error.decode.DecodeException;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.LocalIndex;
import expanse.vm.wasm.util.Slice;

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
