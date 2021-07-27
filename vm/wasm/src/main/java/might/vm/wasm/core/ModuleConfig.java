package might.vm.wasm.core;

import might.common.numeric.I16;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.instruction.Instruction;

import java.util.Map;

/**
 * 模块文件检查要求
 */
public interface ModuleConfig {


    boolean checkVersion(I16 main, I16 min); // 检查版本是否可以接受

    ModuleInstance findModule(String module); // 按模块名查找模块实例

    Map<Instruction, Long> getGasMapping(); // 获取gas映射

}
