package expanse.vm.wasm.model.tag;

import expanse.vm.wasm.error.module.ModuleException;
import expanse.vm.wasm.model.Type;

import static expanse.vm.wasm.util.NumberTransform.toHex;

public class LimitsTag implements Type {

    private final byte value;

    private final String name;

    private LimitsTag(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public static final LimitsTag ZERO = new LimitsTag((byte) 0x00, "0x00");
    public static final LimitsTag ONE  = new LimitsTag((byte) 0x01, "0x01");

    public static LimitsTag of(byte value) {
        switch (value) {
            case 0x00: return ZERO;
            case 0x01: return ONE;
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
    public String dump() {
        return name;
    }

    @Override
    public String toString() {
        return "LimitsTag{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }

}
