package expanse.vm.wasm.instruction.control;

import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.execute.ExecutionException;
import expanse.vm.wasm.instruction.UnreadOperate;
import expanse.vm.wasm.model.Dump;

public class Unreachable implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        throw new ExecutionException("unreachable code");
    }

}
