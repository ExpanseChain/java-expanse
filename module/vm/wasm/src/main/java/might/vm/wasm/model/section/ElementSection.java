package might.vm.wasm.model.section;

import expanse.common.numeric.I32;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.decode.DecodeException;
import might.vm.wasm.error.execute.ExecutionException;
import might.vm.wasm.error.module.ModuleException;
import might.vm.wasm.instruction.Action;
import might.vm.wasm.instruction.Expression;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.Validate;
import might.vm.wasm.model.index.FunctionIndex;
import might.vm.wasm.model.index.TableIndex;
import might.vm.wasm.model.type.ReferenceType;
import might.vm.wasm.model.type.ValueType;
import might.vm.wasm.util.Slice;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static might.vm.wasm.util.NumberTransform.toHex;

/**
 * 这部分貌似有更新，以后再修改
 */
public class ElementSection implements Validate {

    public final byte tag;    // 0x00 ~ 0x07 元素段好多种
    public final Value value; // 元素段内容

    @Override
    public void validate(ModuleInfo info) {
        value.validate(info);
    }

    public static abstract class Value implements Dump, Validate {
        public abstract boolean isActive();
        public abstract void init(ModuleInstance mi);
        protected static void checkExpression(ModuleInfo info, Expression expression) {
            // 表达式要检查一下
            ValueType vt = null;
            for (Action action : expression) {
                switch (action.getInstruction()) {
                    case I32_CONST:
                        vt = ValueType.I32;
                        break;
                    case GLOBAL_GET: break;
                    default: throw new DecodeException("element init expression error.");
                }
            }
            if (vt != ValueType.I32) {
                throw new DecodeException("element init expression error.");
            }
        }
        protected static void checkFunctionIndices(ModuleInfo info, FunctionIndex[] functionIndices) {
            for (FunctionIndex functionIndex : functionIndices) {
                int j = functionIndex.unsigned().intValue();
                Slice.checkArrayIndex(j);
                if (info.functionCount <= j) {
                    throw new DecodeException("can not find function by index: " + j);
                }
            }
        }
        protected static void checkTableIndex(ModuleInfo info, TableIndex tableIndex) {
            long index = tableIndex.unsigned().longValue();
            if (info.tableCount <= index) {
                throw new DecodeException("can not find table by index: " + index);
            }
        }
    }

    public static class Value0 extends Value {
        // 𝟶𝚡𝟶𝟶  𝑒:𝚎𝚡𝚙𝚛  𝑦∗:𝚟𝚎𝚌(𝚏𝚞𝚗𝚌𝚒𝚍𝚡) => {𝗍𝗒𝗉𝖾 𝖿𝗎𝗇𝖼𝗋𝖾𝖿,𝗂𝗇𝗂𝗍 ((𝗋𝖾𝖿.𝖿𝗎𝗇𝖼 𝑦) 𝖾𝗇𝖽)∗,𝗆𝗈𝖽𝖾 𝖺𝖼𝗍𝗂𝗏𝖾 {𝗍𝖺𝖻𝗅𝖾 0,𝗈𝖿𝖿𝗌𝖾𝗍 𝑒}}
        // 第一类是函数索引 初始化
        public Expression expression;
        public FunctionIndex[] functionIndices;
        public Value0(Expression expression, FunctionIndex[] functionIndices) {
            this.expression = expression;
            this.functionIndices = functionIndices;
        }

        @Override
        public String dump() {
            return "0x00 " + expression.dump() + " [" + Stream.of(functionIndices).map(i -> i.unsigned().toString()).collect(Collectors.joining(",")) + "]";
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public void init(ModuleInstance mi) {
            // 计算偏移
            mi.executeExpression(expression);
            I32 offset = mi.popI32();

            // 初始化
            for (int i = 0; i < functionIndices.length; i++) {
                // 默认是0 从初始化的函数表中取出对应的函数
                mi.getTable(TableIndex.of(I32.valueOf(0))).setElement(offset.add(I32.valueOf(i)), mi.getFunction(functionIndices[i]));
            }
        }

        @Override
        public void validate(ModuleInfo info) {
            checkExpression(info, expression);
            checkFunctionIndices(info, functionIndices);
        }
    }
    public static class Value1 extends Value {
        // 𝟶𝚡𝟶𝟷  et:𝚎𝚕𝚎𝚖𝚔𝚒𝚗𝚍  𝑦∗:𝚟𝚎𝚌(𝚏𝚞𝚗𝚌𝚒𝚍𝚡) => {𝗍𝗒𝗉𝖾 et,𝗂𝗇𝗂𝗍 ((𝗋𝖾𝖿.𝖿𝗎𝗇𝖼 𝑦) 𝖾𝗇𝖽)∗,𝗆𝗈𝖽𝖾 𝗉𝖺𝗌𝗌𝗂𝗏𝖾}
        public byte elementKind;
        public FunctionIndex[] functionIndices;

        public Value1(byte elementKind, FunctionIndex[] functionIndices) {
            this.elementKind = elementKind;
            this.functionIndices = functionIndices;
        }

        @Override
        public String dump() {
            return "0x01 " + toHex(elementKind) + " [" + Stream.of(functionIndices).map(i -> i.unsigned().toString()).collect(Collectors.joining(",")) + "]";
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public void init(ModuleInstance mi) {
            throw new ExecutionException("how to init?");
        }

        @Override
        public void validate(ModuleInfo info) {
            checkFunctionIndices(info, functionIndices);
            throw new ModuleException("how to valid?");
        }
    }
    public static class Value2 extends Value {
        // 𝟶𝚡𝟶𝟸  𝑥:𝚝𝚊𝚋𝚕𝚎𝚒𝚍𝚡  𝑒:𝚎𝚡𝚙𝚛  et:𝚎𝚕𝚎𝚖𝚔𝚒𝚗𝚍  𝑦∗:𝚟𝚎𝚌(𝚏𝚞𝚗𝚌𝚒𝚍𝚡) => {𝗍𝗒𝗉𝖾 et,𝗂𝗇𝗂𝗍 ((𝗋𝖾𝖿.𝖿𝗎𝗇𝖼 𝑦) 𝖾𝗇𝖽)∗,𝗆𝗈𝖽𝖾 𝖺𝖼𝗍𝗂𝗏𝖾 {𝗍𝖺𝖻𝗅𝖾 𝑥,𝗈𝖿𝖿𝗌𝖾𝗍 𝑒}}
        public TableIndex tableIndex;
        public Expression expression;
        public byte elementKind;
        public FunctionIndex[] functionIndices;

        public Value2(TableIndex tableIndex, Expression expression, byte elementKind, FunctionIndex[] functionIndices) {
            this.tableIndex = tableIndex;
            this.expression = expression;
            this.elementKind = elementKind;
            this.functionIndices = functionIndices;
        }

        @Override
        public String dump() {
            return "0x02 " + tableIndex + " " + expression.dump() + " " + toHex(elementKind) + " [" + Stream.of(functionIndices).map(i -> i.unsigned().toString()).collect(Collectors.joining(",")) + "]";
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public void init(ModuleInstance mi) {
            throw new ExecutionException("how to init?");
        }

        @Override
        public void validate(ModuleInfo info) {
            checkTableIndex(info, tableIndex);
            checkExpression(info, expression);
            checkFunctionIndices(info, functionIndices);
            throw new ModuleException("how to valid?");
        }
    }
    public static class Value3 extends Value {
        // 𝟶𝚡𝟶𝟹  et:𝚎𝚕𝚎𝚖𝚔𝚒𝚗𝚍  𝑦∗:𝚟𝚎𝚌(𝚏𝚞𝚗𝚌𝚒𝚍𝚡) => {𝗍𝗒𝗉𝖾 et,𝗂𝗇𝗂𝗍 ((𝗋𝖾𝖿.𝖿𝗎𝗇𝖼 𝑦) 𝖾𝗇𝖽)∗,𝗆𝗈𝖽𝖾 𝖽𝖾𝖼𝗅𝖺𝗋𝖺𝗍𝗂𝗏𝖾}
        public byte elementKind;
        public FunctionIndex[] functionIndices;

        public Value3(byte elementKind, FunctionIndex[] functionIndices) {
            this.elementKind = elementKind;
            this.functionIndices = functionIndices;
        }
        @Override
        public String dump() {
            return "0x03 " + toHex(elementKind) + " [" + Stream.of(functionIndices).map(i -> i.unsigned().toString()).collect(Collectors.joining(",")) + "]";
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public void init(ModuleInstance mi) {
            throw new ExecutionException("how to init?");
        }

        @Override
        public void validate(ModuleInfo info) {
            checkFunctionIndices(info, functionIndices);
            throw new ModuleException("how to valid?");
        }
    }
    public static class Value4 extends Value {
        // 𝟶𝚡𝟶𝟺  𝑒:𝚎𝚡𝚙𝚛  el∗:𝚟𝚎𝚌(𝚎𝚡𝚙𝚛) => {𝗍𝗒𝗉𝖾 𝖿𝗎𝗇𝖼𝗋𝖾𝖿,𝗂𝗇𝗂𝗍 el∗,𝗆𝗈𝖽𝖾 𝖺𝖼𝗍𝗂𝗏𝖾 {𝗍𝖺𝖻𝗅𝖾 0,𝗈𝖿𝖿𝗌𝖾𝗍 𝑒}}
        public Expression expression;
        public Expression[] expressionArray;

        public Value4(Expression expression, Expression[] expressionArray) {
            this.expression = expression;
            this.expressionArray = expressionArray;
        }
        @Override
        public String dump() {
            return "0x04 " + expression.dump() + " [" + Stream.of(expressionArray).map(Expression::dump).collect(Collectors.joining(",")) + "]";
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public void init(ModuleInstance mi) {
            // 计算偏移
            mi.executeExpression(expression);
            I32 offset = mi.popI32();

            // 初始化
            for (int i = 0; i < expressionArray.length; i++) {
                mi.executeExpression(expressionArray[i]);
                I32 index = mi.popI32();
                // 默认是0 从初始化的函数表中取出对应的函数
                mi.getTable(TableIndex.of(I32.valueOf(0)))
                        .setElement(offset.add(I32.valueOf(i)), mi.getFunction(FunctionIndex.of(index)));
            }
        }

        @Override
        public void validate(ModuleInfo info) {
            checkExpression(info, expression);
            for (Expression actions : expressionArray) {
                checkExpression(info, actions);
            }
        }
    }
    public static class Value5 extends Value {
        // 𝟶𝚡𝟶𝟻  et:𝚛𝚎𝚏𝚝𝚢𝚙𝚎  el∗:𝚟𝚎𝚌(𝚎𝚡𝚙𝚛) => {𝗍𝗒𝗉𝖾 𝑒𝑡,𝗂𝗇𝗂𝗍 el∗,𝗆𝗈𝖽𝖾 𝗉𝖺𝗌𝗌𝗂𝗏𝖾}
        public ReferenceType referenceType;
        public Expression[] expressionArray;

        public Value5(ReferenceType referenceType, Expression[] expressionArray) {
            this.referenceType = referenceType;
            this.expressionArray = expressionArray;
        }
        @Override
        public String dump() {
            return "0x05 " + referenceType.dump() + " [" + Stream.of(expressionArray).map(Expression::dump).collect(Collectors.joining(",")) + "]";
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public void init(ModuleInstance mi) {
            throw new ExecutionException("how to init?");
        }

        @Override
        public void validate(ModuleInfo info) {
            for (Expression actions : expressionArray) {
                checkExpression(info, actions);
            }
            throw new ModuleException("how to valid?");
        }
    }
    public static class Value6 extends Value {
        // 𝟶𝚡𝟶𝟼  𝑥:𝚝𝚊𝚋𝚕𝚎𝚒𝚍𝚡  𝑒:𝚎𝚡𝚙𝚛  et:𝚛𝚎𝚏𝚝𝚢𝚙𝚎  el∗:𝚟𝚎𝚌(𝚎𝚡𝚙𝚛) => {𝗍𝗒𝗉𝖾 𝑒𝑡,𝗂𝗇𝗂𝗍 el∗,𝗆𝗈𝖽𝖾 𝖺𝖼𝗍𝗂𝗏𝖾 {𝗍𝖺𝖻𝗅𝖾 𝑥,𝗈𝖿𝖿𝗌𝖾𝗍 𝑒}}
        public TableIndex tableIndex;
        public Expression expression;
        public ReferenceType referenceType;
        public Expression[] expressionArray;

        public Value6(TableIndex tableIndex, Expression expression, ReferenceType referenceType, Expression[] expressionArray) {
            this.tableIndex = tableIndex;
            this.expression = expression;
            this.referenceType = referenceType;
            this.expressionArray = expressionArray;
        }
        @Override
        public String dump() {
            return "0x06 " + tableIndex + " " + expression.dump() + " " + referenceType.dump() + " [" + Stream.of(expressionArray).map(Expression::dump).collect(Collectors.joining(",")) + "]";
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public void init(ModuleInstance mi) {
            throw new ExecutionException("how to init?");
        }

        @Override
        public void validate(ModuleInfo info) {
            checkTableIndex(info, tableIndex);
            checkExpression(info, expression);
            for (Expression actions : expressionArray) {
                checkExpression(info, actions);
            }
            throw new ModuleException("how to valid?");
        }
    }
    public static class Value7 extends Value {
        // 𝟶𝚡𝟶𝟽  et:𝚛𝚎𝚏𝚝𝚢𝚙𝚎  el∗:𝚟𝚎𝚌(𝚎𝚡𝚙𝚛) => {𝗍𝗒𝗉𝖾 𝑒𝑡,𝗂𝗇𝗂𝗍 el∗,𝗆𝗈𝖽𝖾 𝖽𝖾𝖼𝗅𝖺𝗋𝖺𝗍𝗂𝗏𝖾}
        public ReferenceType referenceType;
        public Expression[] expressionArray;

        public Value7(ReferenceType referenceType, Expression[] expressionArray) {
            this.referenceType = referenceType;
            this.expressionArray = expressionArray;
        }
        @Override
        public String dump() {
            return "0x07 " + referenceType.dump() + " [" + Stream.of(expressionArray).map(Expression::dump).collect(Collectors.joining(",")) + "]";
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public void init(ModuleInstance mi) {
            throw new ExecutionException("how to init?");
        }

        @Override
        public void validate(ModuleInfo info) {
            for (Expression actions : expressionArray) {
                checkExpression(info, actions);
            }
            throw new ModuleException("how to valid?");
        }
    }


    public ElementSection(byte tag, Value value) {
        this.tag = tag;
        this.value = value;
    }

    public String dump(int index) {
        return "element[" + index + "]: " + value.dump();
    }

}
