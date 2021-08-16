package might.vm.wasm.core;

import expanse.common.numeric.I16;
import might.vm.wasm.core.structure.ModuleInstance;

/**
 * 模块文件检查要求
 */
public interface ModuleConfig {


    boolean checkVersion(I16 main, I16 min); // 检查版本是否可以接受

    ModuleInstance findModule(String module); // 按模块名查找模块实例

}
