package expanse.vm.wasm.core.structure;

import expanse.common.numeric.I32;
import expanse.common.numeric.I64;
import expanse.vm.wasm.model.section.MemoryType;

public interface Memory {

    /**
     * 内存类型
     */
    MemoryType type();

    /**
     * 内存页数
     */
    I32 size();

    /**
     * 内存扩页
     */
    I32 grow(I32 grow);

    /**
     * 读取内存 读取后注意小端法
     */
    void read(I64 offset, byte[] buffer);

    /**
     * 写入内存 写入前注意小端法
     */
    void write(I64 offset, byte[] data);

}
