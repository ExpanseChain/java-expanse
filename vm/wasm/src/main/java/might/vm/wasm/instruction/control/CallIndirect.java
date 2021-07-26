package might.vm.wasm.instruction.control;

import might.common.numeric.I32;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.core.structure.Function;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.error.execute.ExecutionException;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.instruction.dump.DumpCallIndirect;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.TableIndex;
import might.vm.wasm.model.index.TypeIndex;
import might.vm.wasm.model.section.FunctionType;

public class CallIndirect implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpCallIndirect(reader.readTypeIndex(), reader.readTableIndex());
    }

    @Override
    public void valid(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof DumpCallIndirect);
        // 还要检查什么？
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpCallIndirect.class);

        DumpCallIndirect d = (DumpCallIndirect) args;

        int i = mi.popS32();
        // which table ?
        if (mi.getTable(TableIndex.of(I32.valueOf(0))).size().unsigned().intValue() <= i) {
            throw new ExecutionException(String.format("can not find function from table(size:%d) by index: %d",
                    mi.getTable(TableIndex.of(I32.valueOf(0))).size().unsigned().intValue(), i));
        }

        Function function = mi.getTable(TableIndex.of(I32.valueOf(0))).getElement(I32.valueOf(i));

        TypeIndex typeIndex = d.typeIndex;
        FunctionType functionType = mi.getModuleInfo().typeSections.get(typeIndex.unsigned().intValue());
        if (!function.type().same(functionType)) {
            // 检查函数类型是否匹配
            throw new ExecutionException("indirect call type mismatch");
        }

        ((Call) Instruction.CALL.operate).callFunction(mi, function);
    }

}
