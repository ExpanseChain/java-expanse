package might.vm.wasm.core;

import might.common.numeric.I32;
import might.common.numeric.I64;
import might.vm.wasm.instruction.Action;
import might.vm.wasm.instruction.Expression;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.Limits;
import might.vm.wasm.model.Local;
import might.vm.wasm.model.describe.ExportDescribe;
import might.vm.wasm.model.describe.ImportDescribe;
import might.vm.wasm.model.index.*;
import might.vm.wasm.model.section.*;
import might.vm.wasm.model.tag.FunctionTypeTag;
import might.vm.wasm.model.tag.LimitsTag;
import might.vm.wasm.model.tag.PortTag;
import might.vm.wasm.model.type.*;
import might.vm.wasm.util.Leb128;
import might.vm.wasm.util.ModuleConfig;
import might.vm.wasm.util.Slice;

import java.util.ArrayList;
import java.util.List;

import static might.vm.wasm.util.ConstNumber.*;

/**
 * Wasm模块读取流
 */
public class WasmReader {

    private byte[] data;

    public WasmReader(byte[] data) {
        Assertions.requireNonNull(data);

        this.data = data;
    }


    public static ModuleInfo read(byte[] data, ModuleConfig config) {
        return (new WasmReader(data)).readModuleInfo(config);
    }


    public ModuleInfo readModuleInfo(ModuleConfig config) {
        ModuleInfo moduleInfo = new ModuleInfo();

        moduleInfo.magic = new Magic(readByte(), readByte(), readByte(), readByte());
        moduleInfo.version = new Version(readU32(), config);
        moduleInfo.customSections = new Slice<>();

        byte previousSectionId = 0;
        while (this.remaining() > 0) {
            byte sectionId = this.readByte();

            if (sectionId == SECTION_ID_CUSTOM) {
                int size = readLeb128U32().unsigned().intValue();
                byte[] data = new byte[size];
                System.arraycopy(this.data, 0, data, 0, size);
                drop(size);
                moduleInfo.customSections.append(new WasmReader(data).readCustomSection());
                continue;
            }

            // 编号顺序检查
            if (sectionId > SECTION_ID_DATA_COUNT) {
                throw new RuntimeException(String.format("malformed section id: %d", sectionId));
            }
            if (sectionId <= previousSectionId) {
                switch (previousSectionId) {
                    case SECTION_ID_DATA_COUNT:
                        if (SECTION_ID_CODE <= sectionId) { break; }
                        throw new RuntimeException(String.format("junk after last section, id: %d", sectionId));
                    case SECTION_ID_CODE:
                    case SECTION_ID_DATA: break;
                    default:
                        throw new RuntimeException(String.format("junk after last section, id: %d", sectionId));
                }
            }

            previousSectionId = sectionId; // 更新上次id

            // 当前段的长度
            int n = readLeb128U32().unsigned().intValue();

            // 剩下的长度
            int remaining = this.remaining();

            switch (sectionId) {
                case SECTION_ID_TYPE: moduleInfo.typeSections = this.readTypeSections(); break;
                case SECTION_ID_IMPORT: moduleInfo.importSections = this.readImportSections(); break;
                case SECTION_ID_FUNCTION: moduleInfo.functionSections = this.readFunctionSections(); break;
                case SECTION_ID_TABLE: moduleInfo.tableSections = this.readTableSections(); break;
                case SECTION_ID_MEMORY: moduleInfo.memorySections = this.readMemorySections(); break;
                case SECTION_ID_GLOBAL: moduleInfo.globalSections = this.readGlobalSections(); break;
                case SECTION_ID_EXPORT: moduleInfo.exportSections = this.readExportSections(); break;
                case SECTION_ID_START: moduleInfo.startFunctionIndex = FunctionIndex.of(readLeb128U32()); break;
                case SECTION_ID_ELEMENT: moduleInfo.elementSections = this.readElementSections(); break;
                case SECTION_ID_DATA_COUNT: moduleInfo.dataCountIndex = DataCountIndex.of(readLeb128U32()); break;
                case SECTION_ID_CODE: moduleInfo.codeSections = this.readCodeSections(); break;
                case SECTION_ID_DATA: moduleInfo.dataSections = this.readDataSections(); break;
            }

            if (this.remaining() + n != remaining) {
                // 剩下的长度 + 本次段的长度 != 读取段之前剩下的长度
                throw new RuntimeException(String.format("section size mismatch, id: %d", sectionId));
            }
        }

        return moduleInfo;
    }

    // =========================== tool ===========================

    private void drop(int length) {
        Assertions.requireTrue(length <= data.length);

        byte[] d = new byte[data.length - length];
        System.arraycopy(data, length, d, 0, d.length);
        this.data = d;
    }

    public byte readByte() {
        return readByte(true);
    }

    public byte readByte(boolean remove) {
        if (data.length < 1) {
            throw new RuntimeException("unexpected end of section or function");
        }
        byte b = data[0];
        if (remove) { drop(1); }
        return b;
    }

    public I32 readU32() {
        if (data.length < 4) {
            throw new RuntimeException("unexpected end of section or function");
        }
        I32 u32 = I32.valueOf(new byte[]{data[3], data[2], data[1], data[0]});
        drop(4);
        return u32;
    }

    public I32 readLeb128U32() {
        Leb128.Result r = Leb128.readLeb128U32(data);
        drop(r.length);
        return I32.valueOf(r.bytes);
    }

    public int readLeb128S32() {
        Leb128.Result r = Leb128.readLeb128S32(data);
        drop(r.length);
        return I32.valueOf(r.bytes).signed().intValue();
    }

    public I64 readLeb128U64() {
        Leb128.Result r = Leb128.readLeb128U64(data);
        drop(r.length);
        return I64.valueOf(r.bytes);
    }

    public long readLeb128S64() {
        Leb128.Result r = Leb128.readLeb128S64(data);
        drop(r.length);
        return I64.valueOf(r.bytes).signed().longValue();
    }

    public byte[] readBytes() {
        I32 u32 = readLeb128U32();
        int n = u32.unsigned().intValue();
        byte[] bytes = new byte[n];
        System.arraycopy(data, 0, bytes, 0, n);
        drop(n);
        return bytes;
    }

    public String readName() {
        byte[] bytes = this.readBytes();
        return new String(bytes);
    }

    public int remaining() {
        return data.length;
    }

    // =========================== tool ===========================


    // 0

    public CustomSection readCustomSection() {
        return new CustomSection(readName(), this.data);
    }

    // 1

    public Slice<FunctionType> readTypeSections() {
        int n = readLeb128U32().unsigned().intValue();
        Slice<FunctionType> types = new Slice<>(n);
        for (int i = 0; i < n; i++) {
            types.append(readFunctionType());
        }
        return types;
    }

    private FunctionType readFunctionType() {
        byte tag = this.readByte();
        ValueType[] parameterTypes = readValueTypes();
        ValueType[] resultTypes = readValueTypes();
        return new FunctionType(FunctionTypeTag.of(tag), parameterTypes, resultTypes);
    }

    public ValueType[] readValueTypes() {
        int n = readLeb128U32().unsigned().intValue();
        ValueType[] types = new ValueType[n];
        for (int i = 0; i < types.length; i++) {
            types[i] = readValueType();
        }
        return types;
    }

    private ValueType readValueType() {
        byte value = this.readByte();
        return ValueType.of(value);
    }

    // 2

    public Slice<ImportSection> readImportSections() {
        int n = readLeb128U32().unsigned().intValue();
        Slice<ImportSection> importSections = new Slice<>(n);
        for (int i = 0; i < n; i++) {
            importSections.append(new ImportSection(this.readName(), this.readName(), this.readImportDescribe()));
        }
        return importSections;
    }

    public ImportDescribe readImportDescribe() {
        PortTag tag = PortTag.of(this.readByte());
        ImportDescribe.Value value = null;
        switch (tag.value()) {
            case 0x00: // FUNCTION
                value = new ImportDescribe.Function(TypeIndex.of(readLeb128U32())); break;
            case 0x01: // TABLE
                value = new ImportDescribe.Table(readTableType()); break;
            case 0x02: // MEMORY
                value = new ImportDescribe.Memory(readMemoryType()); break;
            case 0x03: // GLOBAL
                value = new ImportDescribe.Global(readGlobalType()); break;
        }
        return new ImportDescribe(tag, value);
    }

    private TableType readTableType() {
        ReferenceType type = ReferenceType.of(this.readByte());

        Limits limits = readLimits();

        return new TableType(type, limits);
    }

    private Limits readLimits() {
        LimitsTag tag = LimitsTag.of(this.readByte());
        I32 min = this.readLeb128U32();
        I32 max = null;
        if (tag == LimitsTag.ONE) {
            max = this.readLeb128U32();
        }
        return new Limits(tag, min, max);
    }

    private MemoryType readMemoryType() {
        Limits limits = this.readLimits();
        return new MemoryType(limits.getTag(), limits.getMin(), limits.getMax());
    }

    private GlobalType readGlobalType() {
        return new GlobalType(ValueType.of(this.readByte()), MutableType.of(this.readByte()));
    }

    // 3

    public Slice<TypeIndex> readFunctionSections() {
        int n = readLeb128U32().unsigned().intValue();
        Slice<TypeIndex> indices = new Slice<>(n);
        for (int i = 0; i < n; i++) {
            indices.append(TypeIndex.of(this.readLeb128U32()));
        }
        return indices;
    }

    // 4

    public Slice<TableType> readTableSections() {
        int n = readLeb128U32().unsigned().intValue();
        Slice<TableType> types = new Slice<>(n);
        for (int i = 0; i < n; i++) {
            types.append(this.readTableType());
        }
        return types;
    }

    // 5

    public Slice<MemoryType> readMemorySections() {
        int n = readLeb128U32().unsigned().intValue();
        Slice<MemoryType> types = new Slice<>(n);
        for (int i = 0; i < n; i++) {
            types.append(readMemoryType());
        }
        return types;
    }

    // 6

    public Slice<GlobalSection> readGlobalSections() {
        int n = readLeb128U32().unsigned().intValue();
        Slice<GlobalSection> globalSections = new Slice<>(n);
        for (int i = 0; i < n; i++) {
            globalSections.append(this.readGlobalSection());
        }
        return globalSections;
    }

    private GlobalSection readGlobalSection() {
        GlobalType type = new GlobalType(ValueType.of(this.readByte()), MutableType.of(this.readByte()));
        Expression expression = readExpression();
        return new GlobalSection(type, expression);
    }

    // 7

    public Slice<ExportSection> readExportSections() {
        int n = readLeb128U32().unsigned().intValue();
        Slice<ExportSection> exportSections = new Slice<>(n);
        for (int i = 0; i < n; i++) {
            exportSections.append(new ExportSection(this.readName(),
                    new ExportDescribe(PortTag.of(this.readByte()), readLeb128U32())));
        }
        return exportSections;
    }

    // 9

    public Slice<ElementSection> readElementSections() {
        int n = readLeb128U32().unsigned().intValue();
        Slice<ElementSection> elementSections = new Slice<>(n);
        for (int i = 0; i < n; i++) {
            elementSections.append(readElementSection());
        }
        return elementSections;
    }

    private ElementSection readElementSection() {
        byte tag = readByte();

        ElementSection.Value value = null;

        switch (tag) {
            case 0x00: value = new ElementSection.Value0(readExpression(), readFunctionIndices()); break;
            case 0x01: value = new ElementSection.Value1(readByte(), readFunctionIndices()); break;
            case 0x02: value = new ElementSection.Value2(TableIndex.of(readLeb128U32()), readExpression(), readByte(), readFunctionIndices()); break;
            case 0x03: value = new ElementSection.Value3(readByte(), readFunctionIndices()); break;
            case 0x04: value = new ElementSection.Value4(readExpression(), readExpresses()); break;
            case 0x05: value = new ElementSection.Value5(ReferenceType.of(readByte()), readExpresses()); break;
            case 0x06: value = new ElementSection.Value6(TableIndex.of(readLeb128U32()), readExpression(), ReferenceType.of(readByte()), readExpresses()); break;
            case 0x07: value = new ElementSection.Value7(ReferenceType.of(readByte()), readExpresses()); break;
        }

        return new ElementSection(tag, value);
    }

    private FunctionIndex[] readFunctionIndices() {
        int n = readLeb128U32().unsigned().intValue();
        FunctionIndex[] indices = new FunctionIndex[n];
        for (int i = 0; i < n; i++) {
            indices[i] = FunctionIndex.of(readLeb128U32());
        }
        return indices;
    }

    private Expression[] readExpresses() {
        int n = readLeb128U32().unsigned().intValue();
        Expression[] expressions = new Expression[n];
        for (int i = 0; i < n; i++) {
            expressions[i] = readExpression();
        }
        return expressions;
    }

    // 10

    public Slice<CodeSection> readCodeSections() {
//        System.out.println(">>>> read codeSection");
        int n = readLeb128U32().unsigned().intValue();
        Slice<CodeSection> codeSections = new Slice<>(n);
        for (int i = 0; i < n; i++) {
            I32 size = this.readLeb128U32();
            int s = size.unsigned().intValue();
//            System.out.println("codeSection [" + i + "] size: " + s);
            byte[] data = new byte[s];
            System.arraycopy(this.data, 0, data, 0, s);
            drop(s);
            WasmReader reader = new WasmReader(data);
            codeSections.append(new CodeSection(size, reader.readLocals(), reader.readExpression()));
        }
        return codeSections;
    }

    private Local[] readLocals() {
        int n = readLeb128U32().unsigned().intValue();
        Local[] locals = new Local[n];
        for (int i = 0; i < locals.length; i++) {
            I32 size = readLeb128U32();
            locals[i] = new Local(size, ValueType.of(this.readByte()));
        }
        return locals;
    }

    // 11

    public Slice<DataSection> readDataSections() {
        int n = readLeb128U32().unsigned().intValue();
        Slice<DataSection> data = new Slice<>(n);
        for (int i = 0; i < n; i++) {
            data.append(readDataSection());
        }
        return data;
    }

    private DataSection readDataSection() {
        byte tag = readByte();

        DataSection.Value value = null;

        switch (tag) {
            case 0x00: value = new DataSection.Value0(readExpression(), readBytes()); break;
            case 0x01: value = new DataSection.Value1(readBytes()); break;
            case 0x02: value = new DataSection.Value2(MemoryIndex.of(readLeb128U32()), readExpression(), readBytes()); break;
        }

        return new DataSection(tag, value);
    }

    // 读取表达式

    public Expression readExpression() {
//        System.out.println("read expressions");
        Action[] actions = readActions();
        byte end = readByte(false);
        if (end != EXPRESSION_END && end != EXPRESSION_ELSE) {
            // 读取完表达式后，末尾应该有结尾
            throw new RuntimeException(String.format("invalid expr end: %d", end));
        }
        if (end == EXPRESSION_END) {
            readByte(); // 如果是else留给IfBlock处理
        }
        return new Expression(actions);
    }

    private Action[] readActions() {
        List<Action> actions = new ArrayList<>();
        while (true) {
            byte end = data[0]; // 一定有字符
            if (end == EXPRESSION_END || end == EXPRESSION_ELSE) {
                return actions.toArray(new Action[0]);
            }
            Action action = readAction();
            actions.add(action);
        }
    }

    private Action readAction() {
        byte opcode = readByte();

        Instruction instruction = Instruction.of(opcode);
//        System.out.println("read expressions codeSection: " + toHex(opcode) + " " + instruction.name);

        switch (instruction.opcode) {
            case (byte) 0x3F: readZero(); break; // 当前指令后面还有个0
            case (byte) 0x40: readZero(); break; // 当前指令后面还有个0
            case (byte) 0xFC:
                I32 n = readLeb128U32();
                switch (n.unsigned().intValue()) {
                    // 表格指令拓展
                    case 12: instruction = Instruction.TABLE_INIT; break;
                    case 13: instruction = Instruction.TABLE_DROP; break;
                    case 14: instruction = Instruction.TABLE_COPY; break;
                    case 15: instruction = Instruction.TABLE_GROW; break;
                    case 16: instruction = Instruction.TABLE_SIZE; break;
                    case 17: instruction = Instruction.TABLE_FILL; break;
                    // 内存指令拓展
                    case  8: instruction = Instruction.MEMORY_INIT; readZero(); break;
                    case  9: instruction = Instruction.DATA_DROP; break;
                    case 10: instruction = Instruction.MEMORY_COPY; readZero(); readZero(); break;
                    case 11: instruction = Instruction.MEMORY_FILL; readZero(); break;
                    // 0~7 浮点数操作相关 不支持
                    // 0 => i32.trunc_sat_f32_s
                    // 1 => i32.trunc_sat_f32_u
                    // 2 => i32.trunc_sat_f64_s
                    // 3 => i32.trunc_sat_f64_u
                    // 4 => i64.trunc_sat_f32_s
                    // 5 => i64.trunc_sat_f32_u
                    // 6 => i64.trunc_sat_f64_s
                    // 7 => i64.trunc_sat_f64_u
                    default: throw new RuntimeException("what ?");
                }
                break;
        }

        Dump args = instruction.readArgs(this);
        return new Action(instruction, args);
    }


    public LocalIndex readLocalIndex() {
        return LocalIndex.of(readLeb128U32());
    }

    public FunctionIndex readFunctionIndex() {
        return FunctionIndex.of(readLeb128U32());
    }

    public GlobalIndex readGlobalIndex() {
        return GlobalIndex.of(readLeb128U32());
    }

    public TableIndex readTableIndex() {
        return TableIndex.of(readLeb128U32());
    }

    public ElementIndex readElementIndex() {
        return ElementIndex.of(readLeb128U32());
    }

    public DataIndex readDataIndex() {
        return DataIndex.of(readLeb128U32());
    }

    private byte readZero() {
        byte zero = readByte();
        if (zero != 0) {
            throw new RuntimeException(String.format("zero flag expected, got %d", zero));
        }
        return 0x0;
    }

    public TypeIndex readTypeIndex() {
        return TypeIndex.of(readLeb128U32());
    }

    public BlockType readBlockType() {
        ValueType valueType = null;
        I64 s33 = null;
        byte b = data[0];
        if ((b & 0b10000000) == 0) {
            // TODO 这就很尴尬了
            //  blocktype ->
            //      0x40 表示空返回值
            //      0x7F I32
            //      0x7E I64
            //      0x7D F32
            //      0x7C F64
            //      0x70 FUNCTION_REFERENCE;
            //      0x6F EXTERN_REFERENCE
            //      其他值。。。。莫名其妙
            b = readByte();
            switch (b) {
                case 0x40: // nil
                case 0x7F: // i32
                case 0x7E: // i64
                case 0x7D: // f32
                case 0x7C: // 64
                case 0x70: // funcref
                case 0x6F: // externref
                    valueType = ValueType.of(b);
                    break;
                default: s33 = I64.valueOf(b);
            }
        } else {
            Leb128.Result s = Leb128.readLeb128S64(data);
            drop(s.length);
            s33 = I64.valueOf(s.bytes);
        }
        return new BlockType(valueType, s33);
    }

    public LabelIndex readLabelIndex() {
        return LabelIndex.of(readLeb128U32());
    }

    public LabelIndex[] readLabelIndices() {
        int n = readLeb128U32().unsigned().intValue();
        LabelIndex[] indices = new LabelIndex[n];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = readLabelIndex();
        }
        return indices;
    }

}
