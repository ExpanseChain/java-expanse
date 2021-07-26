package might.vm.wasm.core2.structure;

import might.vm.wasm.model.section.TableType;
import might.vm.wasm.core2.numeric.U32;

public interface Table {

    /**
     * 表类型
     */
    TableType type();

    /**
     * 表大小
     */
    U32 size();

    /**
     * 表扩容
     */
    void grow(U32 grow);

    /**
     * 获取表中元素
     */
    Function getElement(U32 index);

    /**
     * 设置表元素
     */
    void setElement(U32 index, Function element);

}
