package might.vm.wasm.core2.instruction.control;

import might.vm.wasm.core.error.Assertions;
import might.vm.wasm.core2.instruction.Instruction;
import might.vm.wasm.core2.instruction.dump.DumpBlock;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.model.section.FunctionType;
import might.vm.wasm.core2.structure.ModuleInstance;

public class Loop extends Block {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpBlock.class);

        DumpBlock b = (DumpBlock) args;

        FunctionType functionType = mi.getModuleInfo().getBlockType(b.blockType);

        mi.enterBlock(Instruction.LOOP, functionType, b.expression);
    }

}
