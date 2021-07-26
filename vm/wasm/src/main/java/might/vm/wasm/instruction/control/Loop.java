package might.vm.wasm.instruction.control;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.instruction.dump.DumpBlock;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.section.FunctionType;
import might.vm.wasm.core.structure.ModuleInstance;

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
