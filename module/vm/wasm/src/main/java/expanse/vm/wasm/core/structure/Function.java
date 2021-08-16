package expanse.vm.wasm.core.structure;

import expanse.common.numeric.ISize;
import expanse.vm.wasm.model.section.CodeSection;
import expanse.vm.wasm.model.section.FunctionType;

public interface Function {

    /**
     * 函数签名
     */
    FunctionType type();

    /**
     * 调用函数
     */
    ISize[] call(ISize... args);

    /**
     * 是否内部函数
     */
    default boolean isInternal() { return false; }

    /**
     * 获取内部函数具体内容
     */
    default CodeSection getCodeSection() { return null; }

}
