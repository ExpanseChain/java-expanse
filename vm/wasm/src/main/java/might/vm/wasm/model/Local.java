package might.vm.wasm.model;

import might.common.numeric.I32;
import might.common.numeric.I64;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.decode.DecodeException;
import might.vm.wasm.model.type.ValueType;

/**
 * 记录代码段每个函数的本地变量 每个变量分为一组
 */
public class Local {

    public final I32 size;        // 本地变量长度
    public final ValueType type;  // 本地变量类型

    public Local(I32 size, ValueType type) {
        this.size = size;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Local{" +
                "size=" + size +
                ", type=" + type +
                '}';
    }

    public void pushLocal(ModuleInstance mi) {
        long size = this.size.unsigned().longValue();
        switch (type.value()) {
            case 0x7F: for (long i = 0; i < size; i++) { mi.pushI32(I32.valueOf(0)); } break;
            case 0x7E: for (long i = 0; i < size; i++) { mi.pushI64(I64.valueOf(0)); } break;
//            case 0x7D: return F32;
//            case 0x7C: return F64;
            // 引用类型采用64位吧
            case 0x70: for (long i = 0; i < size; i++) { mi.pushI64(I64.valueOf(0)); } break;
            case 0x6F: for (long i = 0; i < size; i++) { mi.pushI64(I64.valueOf(0)); } break;
            default:
                throw new DecodeException("what a type?");
        }
    }

}
