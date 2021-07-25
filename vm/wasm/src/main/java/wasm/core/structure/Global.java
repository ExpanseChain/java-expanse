package wasm.core.structure;

import wasm.core.model.type.GlobalType;
import wasm.core.numeric.USize;

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
