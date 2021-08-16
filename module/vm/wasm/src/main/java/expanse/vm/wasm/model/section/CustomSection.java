package expanse.vm.wasm.model.section;

import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.model.Validate;

public class CustomSection implements Validate {

    public final String name;
    public final byte[] bytes;

    public CustomSection(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public String dump(int index) {
        return "custom[" + index + "]: name=" + name + " length=" + bytes.length;
    }

    @Override public void validate(ModuleInfo info) {
        // 自定义模块不检查
    }

}
