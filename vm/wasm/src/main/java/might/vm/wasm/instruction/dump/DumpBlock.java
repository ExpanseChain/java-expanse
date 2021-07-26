package might.vm.wasm.instruction.dump;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.instruction.Action;
import might.vm.wasm.instruction.Expression;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.type.BlockType;

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
        blockType.valid(info);
        expression.valid(info, parameters, locals);
    }

}
