package expanse.vm.wasm.instruction;

import expanse.vm.wasm.model.Dump;

public class Action implements Dump {

    private final Instruction instruction;

    private final Dump args;

    public Action(Instruction instruction, Dump args) {
        this.instruction = instruction;
        this.args = args;
    }

    @Override
    public String dump() {
        StringBuilder sb = new StringBuilder();

        sb.append(instruction.name).append(" ");
        if (null != args) { sb.append(args.dump()); }

        return sb.toString();
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public Dump getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return dump();
    }

}
