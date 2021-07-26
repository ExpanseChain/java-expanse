package might.vm.wasm.nav.function;

import might.common.numeric.*;
import might.vm.wasm.core.ControlFrame;
import might.vm.wasm.core.ModuleConfig;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.structure.*;
import might.vm.wasm.error.execute.ExecutionException;
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

public class NativeInstance implements ModuleInstance {

    private static final Map<String, Object> EXPORTS = new HashMap<>();

    static {
        EXPORTS.put("print_char", NativeFunctions.PRINT_CHAR);
        EXPORTS.put("print_i64", NativeFunctions.PRINT_I64);
        EXPORTS.put("print_i32", NativeFunctions.PRINT_I32);

        EXPORTS.put("assert_true", NativeFunctions.ASSERT_TRUE);
        EXPORTS.put("assert_false", NativeFunctions.ASSERT_FALSE);
        EXPORTS.put("assert_eq_i32", NativeFunctions.ASSERT_EQUAL_INT);
        EXPORTS.put("assert_eq_i64", NativeFunctions.ASSERT_EQUAL_LONG);
    }

    @Override
    public Object getMember(String name) {
        return EXPORTS.get(name);
    }

    @Override
    public ISize[] invoke(String name, ISize... args) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void clearOperandStack() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void pushISize(ISize value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void pushI8(I8 value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void pushI16(I16 value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void pushI32(I32 value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void pushI64(I64 value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void pushBool(boolean value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void pushS32(int value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void pushS64(long value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void pushISizes(ISize[] values) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public ISize popISize() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public I8 popI8() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public I16 popI16() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public I32 popI32() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public I64 popI64() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public boolean popBool() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public int popS32() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public long popS64() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public ISize[] popISizes(int size) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public ISize getOperandISize(int index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public I8 getOperandI8(int index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public I16 getOperandI16(int index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public I32 getOperandI32(int index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public I64 getOperandI64(int index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public boolean getOperandBoolean(int index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public int getOperandInt(int index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public long getOperandLong(int index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setOperand(int index, ISize value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setOperand(int index, I8 value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setOperand(int index, I16 value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setOperand(int index, I32 value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setOperand(int index, I64 value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setOperand(int index, boolean value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setOperand(int index, int value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setOperand(int index, long value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public int getFrameOffset() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void clearControlStack() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public ControlFrame popFrame() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public ControlFrame topFrame() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public ControlFrame topCallFrame() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public Memory getMemory(MemoryIndex index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setMemory(MemoryIndex index, Memory value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void write(MemoryIndex index, I64 offset, byte[] data) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void read(MemoryIndex index, I64 offset, byte[] buffer) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void writeBytes(MemoryIndex index, DumpMemory args, byte[] data) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public byte[] readBytes(MemoryIndex index, DumpMemory args, int size) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public I32 memorySize(MemoryIndex index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public I32 memoryGrow(MemoryIndex index, I32 grow) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public Global getGlobal(GlobalIndex index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setGlobal(GlobalIndex index, Global value) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public Function getFunction(FunctionIndex index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setFunction(FunctionIndex index, Function function) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public Table getTable(TableIndex index) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void setTable(TableIndex index, Table table) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void loop() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void executeExpression(Expression expression) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void executeAction(Action action) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void enterBlock(Instruction instruction, FunctionType type, Expression expression) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void exitBlock() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void resetBlock(ControlFrame frame) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public ModuleInfo getModuleInfo() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void linkImports(ModuleConfig config) {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void initFunctions() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void initTables() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void initMemories() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void initGlobals() {
        throw new ExecutionException("not for native module");
    }

    @Override
    public void execStartFunction() {
        throw new ExecutionException("not for native module");
    }

}
