package might.vm.wasm.model.type;

import might.common.numeric.I64;
import might.vm.wasm.model.Type;

public class BlockType implements Type {

    public final ValueType type;

    public final I64 s33;

    public BlockType(ValueType type, I64 s33) {
        this.type = type;
        this.s33 = s33;
    }

    @Override
    public byte value() {
        return null == type ? 0 : type.value();
    }

    @Override
    public String name() {
        return null == type ? "s33:" + s33.signed().longValue() : type.name();
    }

    @Override
    public String dump() {
        return name();
    }

}
