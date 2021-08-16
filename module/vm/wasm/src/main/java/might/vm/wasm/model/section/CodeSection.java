package might.vm.wasm.model.section;

import expanse.common.numeric.I32;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.error.decode.DecodeException;
import might.vm.wasm.instruction.Expression;
import might.vm.wasm.model.Local;
import might.vm.wasm.model.Validate;
import might.vm.wasm.model.index.TypeIndex;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CodeSection implements Validate {

    public final int index;               // 本代码在函数段的序号，再通过函数段指向函数签名段获得参数长度
    public final I32 size;                // 代码大小
    public final Local[] locals;          // 本地变量组
    public final Expression expression;   // 代码的表达式
    public final long localsLength;

    public CodeSection(int index, I32 size, Local[] locals, Expression expression) {
        this.index = index;
        this.size = size;
        this.locals = locals;
        this.expression = expression;

        long length = 0;
        for (Local local : locals) { length += local.size.unsigned().longValue(); }
        this.localsLength = length;
    }

    @Override
    public String toString() {
        return "CodeSection{" +
                "size=" + size.unsigned() +
                ", locals=" + Arrays.toString(locals) +
                ", express=" + expression +
                '}';
    }

    public String dump(int index) {
        StringBuilder sb = new StringBuilder();

        sb.append("func[").append(index).append("]: ")
                .append("locals=[").append(Stream.of(locals).map(l -> l.type.name() + " x " + l.size.unsigned().toString()).collect(Collectors.joining(", "))).append("]");

        if (null != expression && expression.length() > 0) {
            sb.append("\n");
            for (int i = 0; i < expression.length(); i++) {
                sb.append("    ").append(expression.get(i).dump().replace("\n", "\n    ")).append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    @Override
    public void validate(ModuleInfo info) {
        if (info.functionSections.size() <= index) {
            throw new DecodeException("can not find function type index by index: " + index);
        }
        TypeIndex ti = info.functionSections.get(index);
        FunctionType ft = info.typeSections.get(ti);
        expression.valid(info, ft.parameters.length, localsLength);
    }

}
