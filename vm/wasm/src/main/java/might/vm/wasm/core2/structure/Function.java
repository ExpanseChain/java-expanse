package might.vm.wasm.core2.structure;

import might.vm.wasm.core2.model.section.CodeSection;
import might.vm.wasm.core2.model.section.FunctionType;
import might.vm.wasm.core2.numeric.USize;

public interface Function {

    /**
     * 函数签名
     */
    FunctionType type();

    /**
     * 调用函数
     */
    USize[] call(USize... args);

    /**
     * 是否内部函数
     */
    default boolean isInternal() { return false; }

    /**
     * 获取内部函数具体内容
     */
    default CodeSection getCodeSection() { return null; }

}
