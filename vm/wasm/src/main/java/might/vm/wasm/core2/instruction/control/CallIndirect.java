package might.vm.wasm.core2.instruction.control;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.core2.instruction.Instruction;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.instruction.dump.DumpCallIndirect;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.TableIndex;
import might.vm.wasm.model.index.TypeIndex;
import might.vm.wasm.model.section.FunctionType;
import might.vm.wasm.core2.numeric.U32;
import might.vm.wasm.core2.structure.Function;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class CallIndirect implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return new DumpCallIndirect(reader.readTypeIndex(), reader.readTableIndex());
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpCallIndirect.class);

        DumpCallIndirect d = (DumpCallIndirect) args;

        int i = mi.popU32().intValue();
        // which table ?
        if (i >= mi.getTable(TableIndex.of(0)).size().intValue()) {
            throw new RuntimeException("to large");
        }

        Function function = mi.getTable(TableIndex.of(0)).getElement(U32.valueOf(i));

        TypeIndex typeIndex = d.typeIndex;
        FunctionType functionType = mi.getModuleInfo().typeSections[typeIndex.intValue()];
        if (!function.type().same(functionType)) {
            throw new RuntimeException("indirect call type mismatch");
        }

        ((Call) Instruction.CALL.operate).callFunction(mi, function);
    }

}
