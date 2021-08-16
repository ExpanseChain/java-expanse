package expanse.vm.wasm.model.section;

import expanse.vm.wasm.model.describe.ExportDescribe;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.decode.DecodeException;
import expanse.vm.wasm.error.module.ModuleException;
import expanse.vm.wasm.model.Validate;

public class ExportSection implements Validate {

    public final String name;             // 导出名称
    public final ExportDescribe describe; // 导出描述信息

    public ExportSection(String name, ExportDescribe describe) {
        this.name = name;
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "Export{" +
                "name='" + name + '\'' +
                ", describe=" + describe +
                '}';
    }

    public String dump(int index) {
        StringBuilder sb = new StringBuilder();

        switch (describe.tag.value()) {
            case 0x00: // FUNCTION
                sb.append("func[").append(index).append("]: sig=").append(describe.index.unsigned()); break;
            case 0x01: // TABLE
                sb.append("table[").append(index).append("]: sig=").append(describe.index.unsigned()); break;
            case 0x02: // MEMORY
                sb.append("memory[").append(index).append("]: sig=").append(describe.index.unsigned()); break;
            case 0x03: // GLOBAL
                sb.append("global[").append(index).append("]: sig=").append(describe.index.unsigned()); break;
            default:
                throw new DecodeException("what a tag: " + describe.tag.value());
        }
        sb.append(" name=").append(name);

        return sb.toString();
    }

    @Override
    public void validate(ModuleInfo info) {
        // 名称要求不能重复
        if (null == name || name.isEmpty()) {
            throw new ModuleException("export item name can not be empty.");
        }
        if (info.EXPORT_NAMES.contains(name)) {
            throw new ModuleException(String.format("export item name(%s) already exist.", name));
        }
        info.EXPORT_NAMES.add(name);
    }

}
