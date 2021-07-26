package might.vm.wasm.core2.instruction.numeric;

import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.numeric.U32;
import might.vm.wasm.core2.numeric.U64;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class I64ExtendI32U implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return null;
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        U32 value = mi.popU32();
        byte[] bytes = value.getBytes();
        // 无符号拓展，内置方法都是有符号的
        U64 u64 = U64.valueOfU(new byte[] {
            bytes[0], bytes[1], bytes[2], bytes[3]
        });
        mi.pushU64(u64);
    }

}