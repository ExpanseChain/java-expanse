package might.vm.wasm.core2.instruction.control;

import might.vm.wasm.core.error.Assertions;
import might.vm.wasm.core2.instruction.Expression;
import might.vm.wasm.core2.instruction.Instruction;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.instruction.dump.DumpIfBlock;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.model.section.FunctionType;
import might.vm.wasm.core2.model.type.BlockType;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

import static might.vm.wasm.util.ConstNumber.EXPRESSION_ELSE;

public class IfBlock implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        BlockType blockType = reader.readBlockType();
        Expression expression = reader.readExpression();
        Expression expression2 = null;
        if (reader.remaining() > 0) {
            if (reader.readByte(false) == EXPRESSION_ELSE) {
                reader.readByte();
                expression2 = reader.readExpression();
            }
        }
        return new DumpIfBlock(blockType, expression, expression2);
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, DumpIfBlock.class);

        DumpIfBlock b = (DumpIfBlock) args;

        FunctionType functionType = mi.getModuleInfo().getBlockType(b.blockType);

        if (mi.popBool()) {
            mi.enterBlock(Instruction.IF_BLOCK, functionType, b.expression1);
        } else if (null != b.expression2) {
            mi.enterBlock(Instruction.IF_BLOCK, functionType, b.expression2);
        }
    }
}
