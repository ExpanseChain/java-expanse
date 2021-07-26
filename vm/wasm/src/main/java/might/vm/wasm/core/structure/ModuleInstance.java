package might.vm.wasm.core.structure;

import might.common.numeric.*;
import might.vm.wasm.core.ControlFrame;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.instruction.Action;
import might.vm.wasm.instruction.Expression;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.instruction.dump.DumpMemory;
import might.vm.wasm.model.index.FunctionIndex;
import might.vm.wasm.model.index.GlobalIndex;
import might.vm.wasm.model.index.MemoryIndex;
import might.vm.wasm.model.index.TableIndex;
import might.vm.wasm.model.section.FunctionType;

import java.util.HashMap;
import java.util.Map;

/**
 * 每一个模块都必须对外提供导出类型
 */
public interface ModuleInstance {

    Map<String, ModuleInstance> MODULES = new HashMap<>();

    // =================== 模块操作 ===================


    /**
     * 取出指定名称的导出内容
     */
    Object getMember(String name);

    /**
     * 直接调用导出的函数
     */
    ISize[] invoke(String name, ISize... args);


    // =================== 操作数栈操作 ===================

    /**
     * 清除栈上所有值
     */
    void clearOperandStack();

    /** 向栈上推入值 */
    void pushISize(ISize value);
    void pushI8(I8 value);
    void pushI16(I16 value);
    void pushI32(I32 value);
    void pushI64(I64 value);
    void pushBool(boolean value);
    void pushS32(int value);
    void pushS64(long value);
    void pushISizes(ISize[] values);

    /** 弹出值 */
    ISize popISize();
    I8 popI8();
    I16 popI16();
    I32 popI32();
    I64 popI64();
    boolean popBool();
    int popS32();
    long popS64();
    ISize[] popISizes(int size);

    /**
     * 获取操作数栈某个值
     */
    ISize getOperandISize(int index);
    I8 getOperandI8(int index);
    I16 getOperandI16(int index);
    I32 getOperandI32(int index);
    I64 getOperandI64(int index);
    boolean getOperandBoolean(int index);
    int getOperandInt(int index);
    long getOperandLong(int index);
    /**
     * 设置操作数栈某个值
     */
    void setOperand(int index, ISize value);
    void setOperand(int index, I8 value);
    void setOperand(int index, I16 value);
    void setOperand(int index, I32 value);
    void setOperand(int index, I64 value);
    void setOperand(int index, boolean value);
    void setOperand(int index, int value);
    void setOperand(int index, long value);

    /**
     * 获取当前控制栈帧操作数起始位置，也就是第一个函数参数位置
     */
    int getFrameOffset();

    // =================== 控制栈操作 ===================

    /**
     * 清除栈上所有值
     */
    void clearControlStack();

    /**
     * 弹出顶部帧
     */
    ControlFrame popFrame();

    /**
     * 顶部帧
     */
    ControlFrame topFrame();

    /**
     * 顶部函数帧 index是计数
     */
    ControlFrame topCallFrame();

    // =================== 内存操作 ===================

    /**
     * 取出全局变量
     */
    Memory getMemory(MemoryIndex index);

    /**
     * 设置全局变量
     */
    void setMemory(MemoryIndex index, Memory value);

    /**
     * 写入内存
     */
    void write(MemoryIndex index, I64 offset, byte[] data);

    /**
     * 读取内存
     */
    void read(MemoryIndex index, I64 offset, byte[] buffer);

    /**
     * 写入内存
     */
    void writeBytes(MemoryIndex index, DumpMemory args, byte[] data);

    /**
     * 读取内存
     */
    byte[] readBytes(MemoryIndex index, DumpMemory args, int size);

    /**
     * 内存页大小
     */
    I32 memorySize(MemoryIndex index);

    /**
     * 内存扩页
     */
    I32 memoryGrow(MemoryIndex index, I32 grow);

    // =================== 全局变量操作 ===================

    /**
     * 取出全局变量
     */
    Global getGlobal(GlobalIndex index);

    /**
     * 设置全局变量
     */
    void setGlobal(GlobalIndex index, Global value);

    // =================== 函数集合操作 ===================

    /**
     * 取出函数集合
     */
    Function getFunction(FunctionIndex index);

    /**
     * 设置函数
     */
    void setFunction(FunctionIndex index, Function function);


    // =================== 表操作 ===================

    /**
     * 取出表
     */
    Table getTable(TableIndex index);

    /**
     * 设置表
     */
    void setTable(TableIndex index, Table table);


    // =================== 代码操作 ===================

    /**
     * 循环执行函数帧代码
     */
    void loop();

    /**
     * 执行表达式
     */
    void executeExpression(Expression expression);

    /**
     * 执行单个指令
     */
    void executeAction(Action action);

    /**
     * 进入块帧
     */
    void enterBlock(Instruction instruction, FunctionType type, Expression expression);

    /**
     * 退出块帧
     */
    void exitBlock();

    /**
     * 重置块帧
     */
    void resetBlock(ControlFrame frame);

    // =================== 模块信息 ===================

    /**
     * 获取模块信息
     */
    ModuleInfo getModuleInfo();



    // =================== 模块初始化 ===================

    /**
     * 链接导入模块
     */
    void linkImports();

    /**
     * 初始化函数集合
     */
    void initFunctions();

    /**
     * 初始化表
     */
    void initTables();

    /**
     * 初始化内存
     */
    void initMemories();

    /**
     * 初始化全局变量
     */
    void initGlobals();

    /**
     * 执行启动函数
     */
    void execStartFunction();

}
