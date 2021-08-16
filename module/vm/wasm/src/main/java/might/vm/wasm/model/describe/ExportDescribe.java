package might.vm.wasm.model.describe;

import expanse.common.numeric.I32;
import might.vm.wasm.model.tag.PortTag;

public class ExportDescribe {

    public final PortTag tag;   // 导出标识4种类型
    public final I32 index;     // funcidx tableidx memidx globalidx

    public ExportDescribe(PortTag tag, I32 index) {
        this.tag = tag;
        this.index = index;
    }

    @Override
    public String toString() {
        return "ExportDescribe{" +
                "tag=" + tag +
                ", index=" + index.unsigned() +
                '}';
    }

}
