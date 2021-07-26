package might.vm.wasm.instruction.numeric.i64.compare;

import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.UnreadOperate;
import might.vm.wasm.model.Dump;

public class I64Eqz implements UnreadOperate {

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        mi.pushBool(!mi.popI64().booleanValue());
    }

}
