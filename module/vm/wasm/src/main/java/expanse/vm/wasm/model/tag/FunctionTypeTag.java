package expanse.vm.wasm.model.tag;

import expanse.vm.wasm.error.module.ModuleException;
import expanse.vm.wasm.model.Type;

import static expanse.vm.wasm.util.NumberTransform.toHex;

public class FunctionTypeTag implements Type {

    private final byte value;

    private final String name;

    private FunctionTypeTag(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    // 块没有标签 这里随便设置一个标签
    public static final FunctionTypeTag BLOCK_TYPE = new FunctionTypeTag((byte) 0x00, "blocktype");

    public static final FunctionTypeTag FUNCTION_TYPE = new FunctionTypeTag((byte) 0x60, "functype");

    public static FunctionTypeTag of(byte value) {
        switch (value) {
            case 0x60: return FUNCTION_TYPE;
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
        return "0x" + toHex(value) + " " + name;
    }

    @Override
    public String toString() {
        return "FunctionTypeTag{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }

}
