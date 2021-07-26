package might.vm.wasm.model.section;

import might.vm.wasm.core2.instruction.Expression;
import might.vm.wasm.model.Local;
import might.vm.wasm.core2.numeric.U32;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CodeSection {

    public final U32 size;                // 代码大小？
    public final Local[] locals;          // 本地变量组
    public final Expression expression;   // 代码的表达式

    public CodeSection(U32 size, Local[] locals, Expression expression) {
        this.size = size;
        this.locals = locals;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "CodeSection{" +
                "size=" + size +
                ", locals=" + Arrays.toString(locals) +
                ", express=" + expression +
                '}';
    }

    public String dump(int index) {
        StringBuilder sb = new StringBuilder();

        sb.append("func[").append(index).append("]: ")
                .append("locals=[").append(Stream.of(locals).map(l -> l.type.name() + " x " + l.n.dump()).collect(Collectors.joining(", "))).append("]");

        if (null != expression && expression.length() > 0) {
            sb.append("\n");
            for (int i = 0; i < expression.length(); i++) {
                sb.append("    ").append(expression.get(i).dump().replace("\n", "\n    ")).append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

}
