package might.vm.wasm.instance;

import might.common.numeric.I32;
import might.vm.wasm.core.ControlFrame;
import might.vm.wasm.core.ControlStack;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.OperandStack;
import might.vm.wasm.core.structure.*;
import might.vm.wasm.instruction.Action;
import might.vm.wasm.instruction.Expression;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.instruction.dump.DumpMemory;
import might.vm.wasm.model.describe.ExportDescribe;
import might.vm.wasm.model.index.*;
import might.vm.wasm.model.section.*;
import might.vm.wasm.core2.numeric.*;
import might.vm.wasm.util.NumberUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Module implements ModuleInstance {

    private final ModuleInfo moduleInfo;                            // 模块信息

    private final ArrayList<Memory> memories = new ArrayList<>();   // 内存实例
    private final ArrayList<Global> globals = new ArrayList<>();    // 存放全局变量
    private final ArrayList<Function> functions = new ArrayList<>();// 整个模块的函数集合
    private final ArrayList<Table> tables = new ArrayList<>();      // 表

    private final OperandStack operandStack = new OperandStack();   // 操作数栈
    private final ControlStack controlStack = new ControlStack();   // 控制块栈
    protected int frameOffset;                                      // 如果是函数调用，记录函数可用部分的起始位置

    private Map<String, Object> EXPORTS = null;                     // 导出内容缓存

    private Module(ModuleInfo moduleInfo) {
        this.moduleInfo = moduleInfo;
    }

    @Override
    public Object getMember(String name) {
        if (null != EXPORTS) { return EXPORTS.get(name); }

        EXPORTS = new HashMap<>();
        for (int i = 0; i < moduleInfo.exportSections.length; i++) {
            ExportSection exportSection = moduleInfo.exportSections[i];

            if (EXPORTS.containsKey(exportSection.name)) {
                throw new RuntimeException("already exist item: " + exportSection.name);
            }

            ExportDescribe d = exportSection.describe;

            switch (d.tag.value()) {
                case 0x00: // FUNCTION
                    EXPORTS.put(exportSection.name, functions.get(d.index.unsigned().intValue())); break;
                case 0x01: // TABLE
                    EXPORTS.put(exportSection.name, tables.get(d.index.unsigned().intValue())); break;
                case 0x02: // MEMORY
                    EXPORTS.put(exportSection.name, memories.get(d.index.unsigned().intValue())); break;
                case 0x03: // GLOBAL
                    EXPORTS.put(exportSection.name, globals.get(d.index.unsigned().intValue())); break;
                default:
                    throw new RuntimeException("what a tag: " + d.tag);
            }

        }

        return getMember(name);
    }

    @Override
    public USize[] invoke(String name, USize... args) {
        Object member = getMember(name);
        if (member instanceof Function) {
            Function f = (Function) member;

            if (args.length != f.type().parameters.length) {
                throw new RuntimeException("args is mismatch.");
            }

            // 启动一个新的函数，清除栈
            this.clearOperandStack();
            this.clearControlStack();

            return f.call(args);
        }
        throw new RuntimeException("can not find function: " + name);
    }

    @Override
    public void clearOperandStack() {
        operandStack.clear();
    }

    @Override
    public void pushUSize(USize value) {
        operandStack.pushUSize(value);
    }

    @Override
    public void pushU8(U8 value) {
        operandStack.pushU8(value);
    }

    @Override
    public void pushU16(U16 value) {
        operandStack.pushU16(value);
    }

    @Override
    public void pushU32(U32 value) {
        operandStack.pushU32(value);
    }

    @Override
    public void pushU64(U64 value) {
        operandStack.pushU64(value);
    }

    @Override
    public void pushBool(boolean value) {
        operandStack.pushBool(value);
    }

    @Override
    public void pushS32(int value) {
        operandStack.pushS32(value);
    }

    @Override
    public void pushS64(long value) {
        operandStack.pushS64(value);
    }

    @Override
    public void pushUSizes(USize[] values) {
        operandStack.pushUSizes(values);
    }

    @Override
    public USize popUSize() {
        return operandStack.popUSize();
    }

    @Override
    public U8 popU8() {
        return operandStack.popU8();
    }

    @Override
    public U16 popU16() {
        return operandStack.popU16();
    }

    @Override
    public U32 popU32() {
        return operandStack.popU32();
    }

    @Override
    public U64 popU64() {
        return operandStack.popU64();
    }

    @Override
    public boolean popBool() {
        return operandStack.popBool();
    }

    @Override
    public int popS32() {
        return operandStack.popS32();
    }

    @Override
    public long popS64() {
        return operandStack.popS64();
    }

    @Override
    public USize[] popUSizes(int size) {
        return operandStack.popUSizes(size);
    }

    @Override
    public <T extends USize> T getOperand(int index, Class<T> c) {
        return operandStack.getOperand(index, c);
    }

    @Override
    public void setOperand(int index, USize value) {
        operandStack.setOperand(index, value);
    }


    @Override
    public int getFrameOffset() {
        return frameOffset;
    }

    @Override
    public void clearControlStack() {
        controlStack.clear();
    }

    @Override
    public ControlFrame popFrame() {
        return controlStack.pop();
    }

    @Override
    public ControlFrame topFrame() {
        return controlStack.top();
    }

    @Override
    public ControlFrame topCallFrame(int[] index) {
        return controlStack.topCallFrame(index);
    }

    @Override
    public Memory getMemory(MemoryIndex index) {
        return memories.get(index.unsigned().intValue());
    }

    @Override
    public void setMemory(MemoryIndex index, Memory value) {
        while (memories.size() <= index.unsigned().intValue()) { memories.add(null); }
        memories.set(index.unsigned().intValue(), value);
    }

    @Override
    public void write(MemoryIndex index, U64 offset, byte[] data) {
        memories.get(index.unsigned().intValue()).write(offset, data);
    }

    @Override
    public void read(MemoryIndex index, U64 offset, byte[] buffer) {
        memories.get(index.unsigned().intValue()).read(offset, buffer);
    }


    private U64 getOffset(DumpMemory args) {
        U32 offset = args.getOffset();
        U32 immediate = popU32();
        return NumberUtil.add(U64.valueOfU(offset), U64.valueOfU(immediate));
    }

    @Override
    public void writeBytes(MemoryIndex index, DumpMemory args, byte[] data) {
        U64 offset = getOffset(args);
        this.memories.get(index.unsigned().intValue()).write(offset, data);
    }

    @Override
    public byte[] readBytes(MemoryIndex index, DumpMemory args, int size) {
        byte[] bytes = new byte[size];
        U64 offset = getOffset(args);
        this.memories.get(index.unsigned().intValue()).read(offset, bytes);
        return bytes;
    }

    @Override
    public U32 memorySize(MemoryIndex index) {
        return memories.get(index.unsigned().intValue()).size();
    }

    @Override
    public U32 memoryGrow(MemoryIndex index, U32 grow) {
        return memories.get(index.unsigned().intValue()).grow(grow);
    }


    @Override
    public Global getGlobal(GlobalIndex index) {
        return globals.get(index.unsigned().intValue());
    }

    @Override
    public void setGlobal(GlobalIndex index, Global value) {
        while (globals.size() <= index.unsigned().intValue()) { globals.add(null); }
        globals.set(index.unsigned().intValue(), value);
    }

    @Override
    public Function getFunction(FunctionIndex index) {
        return functions.get(index.unsigned().intValue());
    }

    @Override
    public void setFunction(FunctionIndex index, Function function) {
        while (functions.size() <= index.unsigned().intValue()) { functions.add(null); }
        functions.set(index.unsigned().intValue(), function);
    }

    @Override
    public Table getTable(TableIndex index) {
        return tables.get(index.unsigned().intValue());
    }

    @Override
    public void setTable(TableIndex index, Table table) {
        while (tables.size() <= index.unsigned().intValue()) { tables.add(null); }
        tables.set(index.unsigned().intValue(), table);
    }

    @Override
    public void loop() {
        int depth = controlStack.depth();
        while (controlStack.depth() >= depth) {
            ControlFrame frame = controlStack.top();
            if (frame.pc == frame.expression.length()) {
                exitBlock();
            } else {
                Action action = frame.expression.get(frame.pc);
                frame.pc++;
                executeAction(action);
            }
        }
    }

    @Override
    public void executeExpression(Expression expression) {
        for (Action action : expression) {
            executeAction(action);
        }
    }

    @Override
    public void executeAction(Action action) {
//        System.out.println(action.getInstruction().name + " " + (null == action.getArgs() ? "" : action.getArgs().dump()));
        action.getInstruction().operate(this, action.getArgs());
    }

    @Override
    public void enterBlock(Instruction instruction, FunctionType type, Expression expression) {
        int bp = operandStack.size() - type.parameters.length; // 除去参数剩下的栈的长度
        ControlFrame frame = new ControlFrame(bp, instruction, type, expression);
        controlStack.push(frame);
        if (instruction == Instruction.CALL) {
            frameOffset = bp;
        }
    }

    @Override
    public void exitBlock() {
        ControlFrame frame = controlStack.pop();
        clearBlock(frame);
    }

    private void clearBlock(ControlFrame frame) {
        // 取出结果
        USize[] results = popUSizes(frame.functionType.results.length);
        // 弹出参数
        popUSizes(operandStack.size() - frame.bp);
        // 装入结果
        pushUSizes(results);
        if (frame.instruction == Instruction.CALL && controlStack.depth() > 0) {
            ControlFrame callFrame = controlStack.topCallFrame(new int[1]);
            frameOffset = callFrame.bp;
        }
    }

    @Override
    public void resetBlock(ControlFrame frame) {
        USize[] results = popUSizes(frame.functionType.parameters.length);
        popUSizes(operandStack.size() - frame.bp);
        pushUSizes(results);
    }


    @Override
    public ModuleInfo getModuleInfo() {
        return moduleInfo;
    }


    @Override
    public void linkImports() {
        for (int i = 0; i < moduleInfo.importSections.length; i++) {
            ImportSection importSection = moduleInfo.importSections[i];

            ModuleInstance instance = ModuleInstance.MODULES.get(importSection.module);

            if (null == instance) {
                throw new RuntimeException("module instance: " + importSection.module + " is not exist");
            }

            Object member = instance.getMember(importSection.name);

            if (null == member) {
                throw new RuntimeException("can not find export member: " + importSection.name + " in module instance:" + importSection.module);
            }

            if (member instanceof Function) {
                this.setFunction(FunctionIndex.of(I32.valueOf(i)), (Function) member);
            } else if (member instanceof Table) {
                this.setTable(TableIndex.of(I32.valueOf(i)), (Table) member);
            } else if (member instanceof Memory) {
                this.setMemory(MemoryIndex.of(I32.valueOf(i)), (Memory) member);
            } else if (member instanceof Global) {
                this.setGlobal(GlobalIndex.of(I32.valueOf(i)), (Global) member);
            } else {
                throw new RuntimeException("what a member: " + member);
            }
        }
    }

    @Override
    public void initFunctions() {
        for (int i = 0; i < moduleInfo.functionSections.length; i++) {
            TypeIndex index = moduleInfo.functionSections[i];
            FunctionType type = moduleInfo.typeSections[index.unsigned().intValue()];
            CodeSection codeSection = moduleInfo.codeSections[i];
            this.functions.add(new FunctionInstance(type, codeSection, this));
        }
    }

    @Override
    public void initTables() {
        for (int i = 0; i < moduleInfo.tableSections.length; i++) {
            this.tables.add(new TableInstance(moduleInfo.tableSections[i]));
        }
        for (int i = 0; i < moduleInfo.elementSections.length; i++) {
            ElementSection elementSection = moduleInfo.elementSections[i];
            if (elementSection.value.isActive()) {
                elementSection.value.init(this);
            }
        }
    }

    @Override
    public void initMemories() {
        for (int i = 0; i < moduleInfo.memorySections.length; i++) {
            this.memories.add(new MemoryInstance(moduleInfo.memorySections[i]));
        }
        for (DataSection d : moduleInfo.dataSections) {
            d.value.initMemory(this);
        }
    }

    @Override
    public void initGlobals() {
        for (int i = 0; i < moduleInfo.globalSections.length; i++) {
            // 执行初始化指令
            executeExpression(moduleInfo.globalSections[i].init);
            // 将执行结果存到对应位置
            this.globals.add(new GlobalInstance(moduleInfo.globalSections[i].type, popUSize()));
        }
    }

    @Override
    public void execStartFunction() {
        if (null != moduleInfo.startFunctionIndex) {
            getFunction(moduleInfo.startFunctionIndex).call();
        }
    }


    public static ModuleInstance newModule(ModuleInfo info) {
        // TODO 应当先验证下info结构

        Module module = new Module(info);

        module.linkImports();

        module.initFunctions();
        module.initTables();
        module.initMemories();
        module.initGlobals();

        System.out.println("========================= ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ =========================");
        System.out.print(info.dump());
        System.out.println("========================= ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ =========================");

        module.execStartFunction();

        System.out.println();

        return module;
    }

}
