package expanse.vm.wasm.instruction.dump;

import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.instruction.Action;
import expanse.vm.wasm.instruction.Expression;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.type.BlockType;

public class DumpBlock implements Dump {

    public final BlockType blockType;
    public final Expression expression;

    public DumpBlock(BlockType blockType, Expression expression) {
        this.blockType = blockType;
        this.expression = expression;
    }

    @Override
    public String dump() {
        StringBuilder sb = new StringBuilder();

        sb.append("-> ").append(blockType.name()).append("\n");
        for (Action e : expression) {
            sb.append("  ").append(e.dump().replace("\n", "\n  ")).append("\n");
        }
        sb.append("end");

        return sb.toString();
    }

    public void valid(ModuleInfo info, int parameters, long locals) {
        blockType.validate(info);
        expression.valid(info, parameters, locals);
    }

}
