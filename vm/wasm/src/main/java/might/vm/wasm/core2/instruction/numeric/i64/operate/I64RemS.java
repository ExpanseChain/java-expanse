package might.vm.wasm.core2.instruction.numeric.i64.operate;

import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;
import might.vm.wasm.util.NumberUtil;

public class I64RemS implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        long v2 = mi.popS64();
        long v1 = mi.popS64();
        mi.pushS64(NumberUtil.remS(v1, v2));
    }

}
