package might.vm.wasm.instruction;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.model.Dump;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Expression implements Dump, Iterable<Action> {

    private final Action[] actions;

    public Expression(Action[] actions) {
        this.actions = actions;
    }

    public int length() {
        return actions.length;
    }

    public Action get(int index) {
        return actions[index];
    }

    @Override
    public String dump() {
        return "[" + Stream.of(actions).map(Action::dump).collect(Collectors.joining(",")) + "]";
    }

    @Override
    public Iterator<Action> iterator() {
        return Arrays.stream(actions).iterator();
    }

    public void valid(ModuleInfo info, int parameters, long locals) {
        for (Action a : actions) {
            a.getInstruction().valid(info, a.getArgs(), parameters, locals);
        }
    }

}
