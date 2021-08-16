package expanse.vm.wasm.instruction.control;

import expanse.common.numeric.ISize;
import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.core.structure.Function;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.error.module.ModuleException;
import expanse.vm.wasm.instruction.Instruction;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.Local;
import expanse.vm.wasm.model.index.FunctionIndex;
import expanse.vm.wasm.model.section.CodeSection;
import expanse.vm.wasm.util.Slice;

public class Call implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readFunctionIndex();
    }

    @Override
    public void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof FunctionIndex);

        FunctionIndex fi = (FunctionIndex) args;

        int index = fi.unsigned().intValue();
        Slice.checkArrayIndex(index);
        if (info.functionCount <= index) {
            throw new ModuleException("can not find function by index: " + index);
        }
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, FunctionIndex.class);

        FunctionIndex index = ((FunctionIndex) args);

        Function function = mi.getFunction(index);

        callFunction(mi, function);
    }

    public void callFunction(ModuleInstance mi, Function function) {
        if (!function.isInternal()) {
            // 有本地函数内容，对于模块来说是外部的
            callExternalFunction(mi, function);
        } else {
            callInternalFunction(mi, function);
        }
    }

    private void callInternalFunction(ModuleInstance mi, Function function) {
        CodeSection code = function.getCodeSection();
        mi.enterBlock(Instruction.CALL, function.type(), code.expression);

        // 分配本地变量
        for (int i = 0; i < code.locals.length; i++) {
            Local local = code.locals[i];
            local.pushLocal(mi);
        }
    }

    private void callExternalFunction(ModuleInstance mi, Function function) {
        ISize[] args = mi.popISizes(function.type().parameters.length);
        ISize[] results = function.call(args);
        mi.pushISizes(results);
    }

}
