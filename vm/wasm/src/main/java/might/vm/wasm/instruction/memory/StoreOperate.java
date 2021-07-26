package might.vm.wasm.instruction.memory;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.instruction.dump.DumpMemory;
import might.vm.wasm.model.Dump;

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
