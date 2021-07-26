package might.vm.wasm.instance;

import might.vm.wasm.model.type.GlobalType;
import might.vm.wasm.model.type.MutableType;
import might.vm.wasm.core2.numeric.USize;
import might.vm.wasm.core.structure.Global;

public class GlobalInstance implements Global {

    public final GlobalType type;

    public USize value;

    public GlobalInstance(GlobalType type, USize value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public GlobalType type() {
        return type;
    }

    @Override
    public USize get() {
        return value;
    }

    @Override
    public void set(USize value) {
        if (type.mutable != MutableType.MUTABLE) {
            throw new RuntimeException("immutable global");
        }
        this.value = value;
    }

}
