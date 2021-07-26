package might.vm.wasm.core;

import might.vm.wasm.instruction.Expression;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.model.section.FunctionType;

public class ControlFrame {

    public final int bp; // 栈上不属于本控制帧的长度 后面就是传入的参数 以及本地变量栈
    public final Instruction instruction; // 当前控制帧的指令
    public final FunctionType functionType; // 指令是CALL的话 应当函数签名
    public final Expression expression; // 当前块的指令序列
    public int pc; // 当前块应该执行哪条指令

    private int depth; // 当前控制帧距离 栈顶的深度

    public ControlFrame(int bp, Instruction instruction, FunctionType functionType, Expression expression) {
        this.bp = bp;
        this.instruction = instruction;
        this.functionType = functionType;
        this.expression = expression;
        this.pc = 0;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

}
