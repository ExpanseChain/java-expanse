package might.vm.wasm.instruction.control;

import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.execute.ExecutionException;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;

public class Unreachable implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        throw new ExecutionException("unreachable code ?");
    }

}
