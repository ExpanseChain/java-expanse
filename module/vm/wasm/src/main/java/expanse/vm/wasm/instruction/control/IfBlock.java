package expanse.vm.wasm.instruction.control;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.Expression;
import expanse.vm.wasm.instruction.Instruction;
import expanse.vm.wasm.instruction.dump.DumpIfBlock;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.section.FunctionType;
import expanse.vm.wasm.model.type.BlockType;

import static expanse.vm.wasm.util.ConstNumber.EXPRESSION_ELSE;

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
    public void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof DumpIfBlock);

        DumpIfBlock ifBlock = (DumpIfBlock) args;

        ifBlock.expression1.valid(info, parameters, locals);
        if (null != ifBlock.expression2) {
            ifBlock.expression2.valid(info, parameters, locals);
        }
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
