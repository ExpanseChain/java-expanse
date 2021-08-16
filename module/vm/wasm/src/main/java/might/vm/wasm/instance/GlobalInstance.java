package might.vm.wasm.instance;

import expanse.common.numeric.I32;
import expanse.common.numeric.I64;
import expanse.common.numeric.ISize;
import might.vm.wasm.core.structure.Global;
import might.vm.wasm.error.module.ModifyConstException;
import might.vm.wasm.error.module.OperandTypeException;
import might.vm.wasm.model.type.GlobalType;
import might.vm.wasm.model.type.MutableType;

public class GlobalInstance implements Global {

    public final GlobalType type;

    public ISize value;

    public GlobalInstance(GlobalType type, ISize value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public GlobalType type() {
        return type;
    }

    @Override
    public ISize get() {
        return value;
    }

    @Override
    public void set(ISize value) {
        if (type.mutable != MutableType.MUTABLE) {
            throw new ModifyConstException("global variable is immutable");
        }
        switch (type.value.value()) {
            case 0x7F: // I32;
                if (value instanceof I32) break; throw new OperandTypeException("not I32: " + value + "(" + value.getClass().getName() + ")");
            case 0x7E: // I64;
                if (value instanceof I64) break; throw new OperandTypeException("not I64: " + value + "(" + value.getClass().getName() + ")");
//            case 0x7D: // F32;
//            case 0x7C: // F64;

            case 0x70: // FUNCTION_REFERENCE;
                if (value instanceof I64) break; throw new OperandTypeException("not I64: " + value + "(" + value.getClass().getName() + ")");
            case 0x6F: // EXTERN_REFERENCE;
                if (value instanceof I64) break; throw new OperandTypeException("not I64: " + value + "(" + value.getClass().getName() + ")");
        }
        this.value = value;
    }

}
