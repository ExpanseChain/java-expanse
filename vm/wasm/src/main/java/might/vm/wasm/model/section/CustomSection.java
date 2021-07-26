package might.vm.wasm.model.section;

import might.vm.wasm.core.ModuleInfo;

public class CustomSection implements Valid {

    public final String name;
    public final byte[] bytes;

    public CustomSection(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public String dump(int index) {
        return "custom[" + index + "]: name=" + name + " length=" + bytes.length;
    }

    @Override public void valid(ModuleInfo info) {
        // 自定义模块不检查
    }

}
