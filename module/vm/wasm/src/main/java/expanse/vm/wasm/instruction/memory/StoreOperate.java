package expanse.vm.wasm.instruction.memory;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.dump.DumpMemory;
import expanse.vm.wasm.model.Dump;

public interface StoreOperate extends Operate {

    @Override
    default Dump read(WasmReader reader) {
        return new DumpMemory(reader.readLeb128U32(), reader.readLeb128U32());
    }

    @Override
    default void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof DumpMemory);
        // 还有要检查的吗？
    }

}
