package might.vm.wasm.instance;

import might.common.numeric.ISize;
import might.vm.wasm.core.structure.Function;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.instruction.control.Call;
import might.vm.wasm.model.section.CodeSection;
import might.vm.wasm.model.section.FunctionType;

public class FunctionInstance implements Function {

    public final FunctionType type;

    public final CodeSection codeSection;
    private final ModuleInstance instance;

    public final Function function;

    public FunctionInstance(FunctionType type, CodeSection codeSection, ModuleInstance instance) {
        this.type = type;
        this.codeSection = codeSection;
        this.instance = instance;
        this.function = null;
    }

    public FunctionInstance(FunctionType type, Function function) {
        this.instance = null;
        this.type = type;
        this.codeSection = null;
        this.function = function;
    }

    @Override
    public FunctionType type() {
        return type;
    }

    public ISize[] call(ISize... args) {
        if (null != function) {
            return function.call(args);
        }
        return safeCall(instance, args);
    }

    @Override
    public boolean isInternal() {
        return null != codeSection;
    }

    @Override
    public CodeSection getCodeSection() {
        return codeSection;
    }

    protected ISize[] safeCall(ModuleInstance instance, ISize[] args) {
        pushArgs(instance, args);
        ((Call) Instruction.CALL.operate).callFunction(instance, this);
        if (null == function) { instance.loop(); }
        return popResults(instance);
    }

    private void pushArgs(ModuleInstance instance, ISize[] args) {
        Assertions.requireTrue(args.length == type.parameters.length);

        for (ISize arg : args) {
            instance.pushISize(arg);
        }
    }

    protected ISize[] popResults(ModuleInstance instance) {
        ISize[] results = new ISize[type.results.length];
        for (int i = results.length - 1; 0 <= i; i--) {
            results[i] = instance.popISize();
        }
        return results;
    }


}
