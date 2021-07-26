package might.vm.wasm.core2.instance;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.core2.instruction.Instruction;
import might.vm.wasm.core2.instruction.control.Call;
import might.vm.wasm.model.section.CodeSection;
import might.vm.wasm.model.section.FunctionType;
import might.vm.wasm.core2.numeric.U64;
import might.vm.wasm.core2.numeric.USize;
import might.vm.wasm.core2.structure.Function;
import might.vm.wasm.core2.structure.ModuleInstance;

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

    public USize[] call(USize... args) {
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

    protected USize[] safeCall(ModuleInstance instance, USize[] args) {
        pushArgs(instance, args);
        ((Call) Instruction.CALL.operate).callFunction(instance, this);
        if (null == function) { instance.loop(); }
        return popResults(instance);
    }

    private void pushArgs(ModuleInstance instance, USize[] args) {
        Assertions.requireTrue(args.length == type.parameters.length);

        for (USize arg : args) {
            instance.pushUSize(arg);
        }
    }

    protected U64[] popResults(ModuleInstance instance) {
        U64[] results = new U64[type.results.length];
        for (int i = results.length - 1; 0 <= i; i--) {
            results[i] = instance.popU64();
        }
        return results;
    }


}
