package might.vm.wasm.model.section;

import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.error.decode.DecodeException;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.model.describe.ImportDescribe;

public class ImportSection implements Valid {

    public final String module;             // 导入模块名
    public final String name;               // 导入成员名
    public final ImportDescribe describe;   // 具体导入描述信息

    public ImportSection(String module, String name, ImportDescribe describe) {
        this.module = module;
        this.name = name;
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "Import{" +
                "module='" + module + '\'' +
                ", name='" + name + '\'' +
                ", describe=" + describe +
                '}';
    }

    public String dump(int index) {
        StringBuilder sb = new StringBuilder();

        switch (describe.tag.value()) {
            case 0x00: // FUNCTION
                sb.append("func[").append(index).append("]: ")
                        .append(module).append(".").append(name)
                        .append(", sig=").append(describe.value.dump());
                break;
            case 0x01: // TABLE
                sb.append("table[").append(index).append("]: ")
                        .append(module).append(".").append(name)
                        .append(", sig=").append(describe.value.toString());
                break;
            case 0x02: // MEMORY
                sb.append("memory[").append(index).append("]: ")
                        .append(module).append(".").append(name)
                        .append(", sig=").append(describe.value.toString());
                break;
            case 0x03: // GLOBAL
                sb.append("global[").append(index).append("]: ")
                        .append(module).append(".").append(name)
                        .append(", sig=").append(describe.value.toString());
                break;
            default:
                throw new DecodeException("what a tag: " + describe.tag.value());
        }

        return sb.toString();
    }

    @Override
    public void valid(ModuleInfo info) {
        // 导入段
        if (null == module || module.isEmpty()) {
            throw new ModuleException("module can not be empty.");
        }
        if (null == name || name.isEmpty()) {
            throw new ModuleException("module can not be empty.");
        }
        this.describe.value.valid(info);
    }

}
