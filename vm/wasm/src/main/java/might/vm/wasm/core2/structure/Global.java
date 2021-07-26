package might.vm.wasm.core2.structure;

import might.vm.wasm.core2.model.type.GlobalType;
import might.vm.wasm.core2.numeric.USize;

public interface Global {

    /**
     * 全局数据类型
     */
    GlobalType type();

    /**
     * 获取全局参数
     */
    USize get();

    /**
     * 设置全局参数
     */
    void set(USize value);

}
