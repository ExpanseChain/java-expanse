package wasm.core.structure;

import wasm.core.model.section.CodeSection;
import wasm.core.model.section.FunctionType;
import wasm.core.numeric.USize;

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
