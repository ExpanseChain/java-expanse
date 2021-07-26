package might.vm.wasm.util;

import might.common.numeric.I16;

/**
 * 模块文件检查要求
 */
public interface ModuleConfig {


    boolean checkVersion(I16 main, I16 min); // 检查版本是否可以接受




}
