package expanse.vm.wasm.instruction.control;

import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.Instruction;
import expanse.vm.wasm.instruction.dump.DumpBlock;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.section.FunctionType;
import expanse.vm.wasm.core.structure.ModuleInstance;

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
