package might.vm.wasm.model.type;

import expanse.common.numeric.I64;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.error.decode.DecodeException;
import might.vm.wasm.model.Type;
import might.vm.wasm.model.Validate;

public class BlockType implements Type, Validate {

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

    @Override
    public void validate(ModuleInfo info) {
        if (null == type) {
            // 验证是否有对应函数签名 s33

            int max = info.typeSections.size();

            if (max <= s33.signed().longValue()) {
                throw new DecodeException("can not find function type by s33: " + s33.signed().longValue());
            }
        }
    }

}
