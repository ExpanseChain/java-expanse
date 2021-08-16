package expanse.vm.wasm.model.type;

import expanse.vm.wasm.error.module.ModuleException;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.Type;

import static expanse.vm.wasm.util.NumberTransform.toHex;

public class ReferenceType implements Type, Dump {

    private final byte value;

    private final String name;

    private ReferenceType(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    private static final ReferenceType FUNCTION_REFERENCE = new ReferenceType((byte) 0x70, "funcref");
    private static final ReferenceType EXTERN_REFERENCE   = new ReferenceType((byte) 0x6F, "externref");

    public static ReferenceType of(byte value) {
        switch (value) {
            case 0x70: return FUNCTION_REFERENCE;
            case 0x6F: return EXTERN_REFERENCE;
        }
        throw new ModuleException("wrong value: 0x" + toHex(value));
    }

    @Override
    public byte value() {
        return value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "ReferenceType{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String dump() {
        return name + "[0x" + toHex(value) + "]";
    }

}
