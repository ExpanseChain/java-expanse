package might.vm.wasm.instance;

import might.common.numeric.*;
import might.vm.wasm.core.ControlFrame;
import might.vm.wasm.core.ControlStack;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.OperandStack;
import might.vm.wasm.core.structure.*;
import might.vm.wasm.error.execute.ExecutionException;
import might.vm.wasm.error.instance.InstanceException;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.instruction.Action;
import might.vm.wasm.instruction.Expression;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.instruction.dump.DumpMemory;
import might.vm.wasm.model.describe.ExportDescribe;
import might.vm.wasm.model.index.*;
import might.vm.wasm.model.section.*;
import might.vm.wasm.util.Slice;

import java.util.HashMap;
import java.util.Map;

public class Module implements ModuleInstance {

    private final ModuleInfo moduleInfo;                            // 模块信息

    private final Slice<Memory> memories = new Slice<>();   // 内存实例
    private final Slice<Global> globals = new Slice<>();    // 存放全局变量
    private final Slice<Function> functions = new Slice<>();// 整个模块的函数集合
    private final Slice<Table> tables = new Slice<>();      // 表

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
        for (int i = 0; i < moduleInfo.exportSections.size(); i++) {
            ExportSection exportSection = moduleInfo.exportSections.get(i);

            if (EXPORTS.containsKey(exportSection.name)) {
                throw new ExecutionException("already exist item: " + exportSection.name);
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
                    throw new InstanceException("what a tag: " + d.tag);
            }

        }

        return getMember(name);
    }

    @Override
    public ISize[] invoke(String name, ISize... args) {
        Object member = getMember(name);
        if (member instanceof Function) {
            Function f = (Function) member;

            if (args.length != f.type().parameters.length) {
                throw new ExecutionException("args is mismatch.");
            }

            // 启动一个新的函数，清除栈
            this.clearOperandStack();
            this.clearControlStack();

            return f.call(args);
        }
        throw new ExecutionException("can not find function: " + name);
    }

    @Override
    public void clearOperandStack() {
        operandStack.clear();
    }

    @Override
    public void pushISize(ISize value) {
        operandStack.pushISize(value);
    }

    @Override
    public void pushI8(I8 value) {
        operandStack.pushI8(value);
    }

    @Override
    public void pushI16(I16 value) {
        operandStack.pushI16(value);
    }

    @Override
    public void pushI32(I32 value) {
        operandStack.pushI32(value);
    }

    @Override
    public void pushI64(I64 value) {
        operandStack.pushI64(value);
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
    public void pushISizes(ISize[] values) {
        operandStack.pushUSizes(values);
    }

    @Override
    public ISize popISize() {
        return operandStack.popISize();
    }

    @Override
    public I8 popI8() {
        return operandStack.popI8();
    }

    @Override
    public I16 popI16() {
        return operandStack.popI16();
    }

    @Override
    public I32 popI32() {
        return operandStack.popI32();
    }

    @Override
    public I64 popI64() {
        return operandStack.popI64();
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
    public ISize[] popISizes(int size) {
        return operandStack.popUSizes(size);
    }

    @Override
    public ISize getOperandISize(int index) {
        return operandStack.getOperandISize(index);
    }

    @Override
    public I8 getOperandI8(int index) {
        return operandStack.getOperandI8(index);
    }

    @Override
    public I16 getOperandI16(int index) {
        return operandStack.getOperandI16(index);
    }

    @Override
    public I32 getOperandI32(int index) {
        return operandStack.getOperandI32(index);
    }

    @Override
    public I64 getOperandI64(int index) {
        return operandStack.getOperandI64(index);
    }

    @Override
    public boolean getOperandBoolean(int index) {
        return operandStack.getOperandBoolean(index);
    }

    @Override
    public int getOperandInt(int index) {
        return operandStack.getOperandInt(index);
    }

    @Override
    public long getOperandLong(int index) {
        return operandStack.getOperandLong(index);
    }

    @Override
    public void setOperand(int index, ISize value) {
        operandStack.setOperand(index, value);
    }

    @Override
    public void setOperand(int index, I8 value) {
        operandStack.setOperand(index, value);
    }

    @Override
    public void setOperand(int index, I16 value) {
        operandStack.setOperand(index, value);
    }

    @Override
    public void setOperand(int index, I32 value) {
        operandStack.setOperand(index, value);
    }

    @Override
    public void setOperand(int index, I64 value) {
        operandStack.setOperand(index, value);
    }

    @Override
    public void setOperand(int index, boolean value) {
        operandStack.setOperand(index, value);
    }

    @Override
    public void setOperand(int index, int value) {
        operandStack.setOperand(index, value);
    }

    @Override
    public void setOperand(int index, long value) {
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
    public ControlFrame topCallFrame() {
        return controlStack.topCallFrame();
    }

    @Override
    public Memory getMemory(MemoryIndex index) {
        return memories.get(index.unsigned().intValue());
    }

    @Override
    public void setMemory(MemoryIndex index, Memory value) {
        memories.set(index.unsigned().intValue(), value);
    }

    @Override
    public void write(MemoryIndex index, I64 offset, byte[] data) {
        memories.get(index).write(offset, data);
    }

    @Override
    public void read(MemoryIndex index, I64 offset, byte[] buffer) {
        memories.get(index).read(offset, buffer);
    }

    private I64 getOffset(DumpMemory args) {
        I32 offset = args.getOffset();
        I32 immediate = popI32();
        return offset.u64().add(immediate.u64());
    }

    @Override
    public void writeBytes(MemoryIndex index, DumpMemory args, byte[] data) {
        I64 offset = getOffset(args);
        this.memories.get(index.unsigned().intValue()).write(offset, data);
    }

    @Override
    public byte[] readBytes(MemoryIndex index, DumpMemory args, int size) {
        byte[] bytes = new byte[size];
        I64 offset = getOffset(args);
        this.memories.get(index.unsigned().intValue()).read(offset, bytes);
        return bytes;
    }

    @Override
    public I32 memorySize(MemoryIndex index) {
        return memories.get(index.unsigned().intValue()).size();
    }

    @Override
    public I32 memoryGrow(MemoryIndex index, I32 grow) {
        return memories.get(index.unsigned().intValue()).grow(grow);
    }

    @Override
    public Global getGlobal(GlobalIndex index) {
        return globals.get(index.unsigned().intValue());
    }

    @Override
    public void setGlobal(GlobalIndex index, Global value) {
        globals.set(index.unsigned().intValue(), value);
    }

    @Override
    public Function getFunction(FunctionIndex index) {
        return functions.get(index.unsigned().intValue());
    }

    @Override
    public void setFunction(FunctionIndex index, Function function) {
        functions.set(index.unsigned().intValue(), function);
    }

    @Override
    public Table getTable(TableIndex index) {
        return tables.get(index.unsigned().intValue());
    }

    @Override
    public void setTable(TableIndex index, Table table) {
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
        ISize[] results = popISizes(frame.functionType.results.length);
        // 弹出参数
        popISizes(operandStack.size() - frame.bp);
        // 装入结果
        pushISizes(results);
        if (frame.instruction == Instruction.CALL && controlStack.depth() > 0) {
            ControlFrame callFrame = controlStack.topCallFrame();
            frameOffset = callFrame.bp;
        }
    }

    @Override
    public void resetBlock(ControlFrame frame) {
        ISize[] results = popISizes(frame.functionType.parameters.length);
        popISizes(operandStack.size() - frame.bp);
        pushISizes(results);
    }


    @Override
    public ModuleInfo getModuleInfo() {
        return moduleInfo;
    }


    @Override
    public void linkImports() {
        for (int i = 0; i < moduleInfo.importSections.size(); i++) {
            ImportSection importSection = moduleInfo.importSections.get(i);

            ModuleInstance instance = ModuleInstance.MODULES.get(importSection.module);

            if (null == instance) {
                throw new ModuleException("module instance: " + importSection.module + " is not exist");
            }

            Object member = instance.getMember(importSection.name);

            if (null == member) {
                throw new ModuleException("can not find export member: " + importSection.name + " in module instance:" + importSection.module);
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
                throw new ModuleException("what a member: " + member);
            }
        }
    }

    @Override
    public void initFunctions() {
        for (int i = 0; i < moduleInfo.functionSections.size(); i++) {
            TypeIndex index = moduleInfo.functionSections.get(i);
            FunctionType type = moduleInfo.typeSections.get(index.unsigned().intValue());
            CodeSection codeSection = moduleInfo.codeSections.get(i);
            this.functions.append(new FunctionInstance(type, codeSection, this));
        }
    }

    @Override
    public void initTables() {
        for (int i = 0; i < moduleInfo.tableSections.size(); i++) {
            this.tables.append(new TableInstance(moduleInfo.tableSections.get(i)));
        }
        for (int i = 0; i < moduleInfo.elementSections.size(); i++) {
            ElementSection elementSection = moduleInfo.elementSections.get(i);
            if (elementSection.value.isActive()) {
                elementSection.value.init(this);
            }
        }
    }

    @Override
    public void initMemories() {
        for (int i = 0; i < moduleInfo.memorySections.size(); i++) {
            this.memories.append(new MemoryInstance(moduleInfo.memorySections.get(i)));
        }
        for (DataSection d : moduleInfo.dataSections) {
            d.value.initMemory(this);
        }
    }

    @Override
    public void initGlobals() {
        for (int i = 0; i < moduleInfo.globalSections.size(); i++) {
            // 执行初始化指令
            executeExpression(moduleInfo.globalSections.get(i).init);
            // 将执行结果存到对应位置
            this.globals.append(new GlobalInstance(moduleInfo.globalSections.get(i).type, popISize()));
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
