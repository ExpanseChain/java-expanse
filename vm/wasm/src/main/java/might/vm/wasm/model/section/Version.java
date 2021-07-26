package might.vm.wasm.model.section;

import might.common.numeric.I16;
import might.common.numeric.I32;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.util.ModuleConfig;

/**
 * Wasm定义是一个U32数字
 * 这边解析成2个Leb128的数字
 * 前一个是主版本 主版本不同互不兼容
 * 后一个是服版本 副版本大的可以兼容副版本小的
 *
 * 01 00 00 00
 *
 * 不行不行，不兼容
 */
public class Version {

    private final I16 main;
    private final I16 sub;

    public Version(I32 i32, ModuleConfig config) {
        Assertions.requireNonNull(i32);
        Assertions.requireNonNull(config);

        this.main = I16.valueOf(new byte[]{ i32.bytes()[0], i32.bytes()[1]});
        this.sub  = I16.valueOf(new byte[]{ i32.bytes()[2], i32.bytes()[3]});

        // 版本怎么检查？
        if (config.getMinMainVersion().lessOrEqualsU(main)
                && main.lessOrEqualsU(config.getMaxMainVersion())
                && config.getMinSubVersion().lessOrEqualsU(sub)
                && sub.lessOrEqualsU(config.getMaxSubVersion())
        ) {

        } else {
            throw new ModuleException("version is not acceptable: " + i32.toHexString());
        }
    }

    public String value() {
        return main.toHexString() + sub.toHexString();
    }

    @Override
    public String toString() {
        return "Version: " + main.toBinaryString() + sub.toBinaryString();
    }

}
