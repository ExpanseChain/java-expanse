package might.vm.wasm.core2.instruction.control;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.core2.instruction.Expression;
import might.vm.wasm.core2.instruction.Instruction;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.instruction.dump.DumpBlock;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.section.FunctionType;
import might.vm.wasm.model.type.BlockType;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class Block implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        BlockType blockType = reader.readBlockType();
        Expression expression = reader.readExpression();
        return new DumpBlock(blockType, expression);
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpBlock.class);

        DumpBlock b = (DumpBlock) args;

        FunctionType functionType = mi.getModuleInfo().getBlockType(b.blockType);

        mi.enterBlock(Instruction.BLOCK, functionType, b.expression);
    }

}
