package might.vm.wasm.core2.instruction.reference;

import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.core2.structure.WasmReader;

public class RefIsNull implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

}
