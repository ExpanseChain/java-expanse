package might.vm.wasm.core.structure;

import might.common.numeric.I32;
import might.vm.wasm.model.section.TableType;

public interface Table {

    /**
     * 表类型
     */
    TableType type();

    /**
     * 表大小
     */
    I32 size();

    /**
     * 表扩容
     */
    void grow(I32 grow);

    /**
     * 获取表中元素
     */
    Function getElement(I32 index);

    /**
     * 设置表元素
     */
    void setElement(I32 index, Function element);

}
