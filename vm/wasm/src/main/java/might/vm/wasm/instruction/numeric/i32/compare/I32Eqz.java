package might.vm.wasm.instruction.numeric.i32.compare;

import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;

public class I32Eqz implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        mi.pushBool(!mi.popI32().booleanValue());
    }

}
