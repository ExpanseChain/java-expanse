package might.vm.wasm.model.section;

import might.common.numeric.I32;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.decode.DecodeException;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.instruction.Action;
import might.vm.wasm.instruction.Expression;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.MemoryIndex;
import might.vm.wasm.model.type.ValueType;
import might.vm.wasm.util.NumberTransform;
import might.vm.wasm.util.Slice;

import static might.vm.wasm.util.NumberTransform.toHexArray;

public class DataSection implements Valid {

    public byte tag;    // 数据标签
    public Value value; // 数据内容

    @Override
    public void valid(ModuleInfo info) {
        value.valid(info);
    }

    public static abstract class Value implements Dump, Valid {
        public abstract void initMemory(ModuleInstance mi);
        protected static void checkExpression(ModuleInfo info, Expression expression) {
            // 表达式要检查一下
            ValueType vt = null;
            for (Action action : expression) {
                switch (action.getInstruction()) {
                    case I32_CONST:
                        vt = ValueType.I32;
                        break;
                    case GLOBAL_GET: break;
                    default: throw new DecodeException("data init expression error.");
                }
            }
            if (vt != ValueType.I32) {
                throw new DecodeException("data init expression error.");
            }
        }
    }

    public static class Value0 extends Value {
        // 𝟶𝚡𝟶𝟶  𝑒:𝚎𝚡𝚙𝚛  𝑏∗:𝚟𝚎𝚌(𝚋𝚢𝚝𝚎) => {𝗂𝗇𝗂𝗍 𝑏∗,𝗆𝗈𝖽𝖾 𝖺𝖼𝗍𝗂𝗏𝖾 {𝗆𝖾𝗆𝗈𝗋𝗒 0,𝗈𝖿𝖿𝗌𝖾𝗍 𝑒}}
        public Expression expression;
        public byte[] bytes;

        public Value0(Expression expression, byte[] bytes) {
            this.expression = expression;
            this.bytes = bytes;
        }

        @Override
        public String dump() {
            return "0x00 " + expression.dump() + " [" + NumberTransform.toHexArray(bytes) + "]";
        }

        @Override
        public void initMemory(ModuleInstance mi) {
            mi.executeExpression(expression);
            I32 offset = mi.popI32();

            mi.write(MemoryIndex.of(I32.valueOf(0)), offset.u64(), bytes);
        }

        @Override
        public void valid(ModuleInfo info) {
            checkExpression(info, expression);
        }
    }
    public static class Value1 extends Value {
        // 𝟶𝚡𝟶𝟷  𝑏∗:𝚟𝚎𝚌(𝚋𝚢𝚝𝚎) => {𝗂𝗇𝗂𝗍 𝑏∗,𝗆𝗈𝖽𝖾 𝗉𝖺𝗌𝗌𝗂𝗏𝖾}
        public byte[] bytes;

        public Value1(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public String dump() {
            return "0x01 [" +  toHexArray(bytes) + "]";
        }

        // 非主动初始化内存
        @Override
        public void initMemory(ModuleInstance mi) { }

        @Override
        public void valid(ModuleInfo info) { }

    }
    public static class Value2 extends Value {
        // 𝟶𝚡𝟶𝟸  𝑥:𝚖𝚎𝚖𝚒𝚍𝚡  𝑒:𝚎𝚡𝚙𝚛  𝑏∗:𝚟𝚎𝚌(𝚋𝚢𝚝𝚎) => {𝗂𝗇𝗂𝗍 𝑏∗,𝗆𝗈𝖽𝖾 𝖺𝖼𝗍𝗂𝗏𝖾 {𝗆𝖾𝗆𝗈𝗋𝗒 𝑥,𝗈𝖿𝖿𝗌𝖾𝗍 𝑒}}
        public MemoryIndex memoryIndex;
        public Expression expression;
        public byte[] bytes;

        public Value2(MemoryIndex memoryIndex, Expression expression, byte[] bytes) {
            this.memoryIndex = memoryIndex;
            this.expression = expression;
            this.bytes = bytes;
        }

        @Override
        public String dump() {
            return "0x02 " + memoryIndex.toString() + " " + expression.dump() + " [" +  toHexArray(bytes) + "]";
        }

        @Override
        public void initMemory(ModuleInstance mi) {
            int index = memoryIndex.unsigned().intValue();

            mi.executeExpression(expression);
            I32 offset = mi.popI32();

            mi.write(MemoryIndex.of(I32.valueOf(index)), offset.u64(), bytes);
        }

        @Override
        public void valid(ModuleInfo info) {
            // 检查memory
            int mi = memoryIndex.unsigned().intValue();
            Slice.checkArrayIndex(mi);
            if (info.memoryCount <= mi) {
                throw new ModuleException("can not find memory by index: " + mi);
            }
            checkExpression(info, expression);
        }
    }


    public DataSection(byte tag, Value value) {
        this.tag = tag;
        this.value = value;
    }

    public String dump(int index) {
        return "data[" + index + "]: " + value.dump();
    }

}
