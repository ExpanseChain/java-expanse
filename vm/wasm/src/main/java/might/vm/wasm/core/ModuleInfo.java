package might.vm.wasm.core;

import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.execute.ExecutionException;
import might.vm.wasm.model.index.DataCountIndex;
import might.vm.wasm.model.index.FunctionIndex;
import might.vm.wasm.model.index.TypeIndex;
import might.vm.wasm.model.section.*;
import might.vm.wasm.model.tag.FunctionTypeTag;
import might.vm.wasm.model.type.BlockType;
import might.vm.wasm.model.type.ValueType;
import might.vm.wasm.nav.function.NativeInstance;
import might.vm.wasm.util.ValidSlice;

import java.util.HashSet;
import java.util.Set;


public class ModuleInfo {
    
    static  {
        ModuleInstance.MODULES.put("env", new NativeInstance());
    }

    public Magic magic;         // 魔数
    public Version version;     // 版本

    public final ValidSlice<CustomSection> customSections = new ValidSlice<>();     //  0 自定义段
    public ValidSlice<FunctionType> typeSections = new ValidSlice<>();        //  1 类型段 函数签名
    public ValidSlice<ImportSection> importSections = new ValidSlice<>();     //  2 导入 导入函数部分指向类型段的函数签名
    public ValidSlice<TypeIndex> functionSections = new ValidSlice<>();       //  3 函数段 指向类型段函数索引
    public ValidSlice<TableType> tableSections = new ValidSlice<>();          //  4 表
    public ValidSlice<MemoryType> memorySections = new ValidSlice<>();        //  5 内存
    public ValidSlice<GlobalSection> globalSections = new ValidSlice<>();     //  6 全局
    public ValidSlice<ExportSection> exportSections = new ValidSlice<>();     //  7 导出
    public FunctionIndex startFunctionIndex;                                  //  8 起始函数索引 uint32 应当是函数段的索引
    public ValidSlice<ElementSection> elementSections = new ValidSlice<>();   //  9 元素
    public DataCountIndex dataCountIndex;                                     // 12 数据计数段 memory.init data.drop 需要数据
    public ValidSlice<CodeSection> codeSections = new ValidSlice<>();         // 10 代码
    public ValidSlice<DataSection> dataSections = new ValidSlice<>();         // 11 数据

    public final Set<String> EXPORT_NAMES = new HashSet<>(); // 导出模块名称记录
    public long importFunctionCount = 0;  // 导入函数个数
    public long importTableCount = 0;     // 导入表个数
    public long importMemoryCount = 0;    // 导入内存个数
    public long importGlobalCount = 0;    // 导入全局变量个数
    public long functionCount = 0;  // 函数个数
    public long tableCount = 0;     // 表个数
    public long memoryCount = 0;    // 内存个数
    public long globalCount = 0;    // 全局变量个数

    ModuleInfo() {}

    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append("Magic: ").append(magic.value()).append("\n");
        sb.append("Version: ").append(version.value()).append("\n");

        sb.append("Type[").append(typeSections.size()).append("]:").append("\n");
        for (int i = 0; i < typeSections.size(); i++) {
            sb.append("  ").append(typeSections.get(i).dump(i)).append("\n");
        }

        sb.append("Import[").append(importSections.size()).append("]:").append("\n");
        for (int i = 0; i < importSections.size(); i++) {
            sb.append("  ").append(importSections.get(i).dump(i)).append("\n");
        }

        sb.append("Function[").append(functionSections.size()).append("]:").append("\n");
        for (int i = 0; i < functionSections.size(); i++) {
            sb.append("  ").append(functionSections.get(i).dump((int)(importFunctionCount + i))).append("\n");
        }

        sb.append("Table[").append(tableSections.size()).append("]:").append("\n");
        for (int i = 0; i < tableSections.size(); i++) {
            sb.append("  ").append(tableSections.get(i).dump((int)(importTableCount + i))).append("\n");
        }

        sb.append("Memory[").append(memorySections.size()).append("]:").append("\n");
        for (int i = 0; i < memorySections.size(); i++) {
            sb.append("  ").append(memorySections.get(i).dump((int)(importMemoryCount + i))).append("\n");
        }

        sb.append("Global[").append(globalSections.size()).append("]:").append("\n");
        for (int i = 0; i < globalSections.size(); i++) {
            sb.append("  ").append(globalSections.get(i).dump((int)(importGlobalCount + i))).append("\n");
        }

        sb.append("Export[").append(exportSections.size()).append("]:").append("\n");
        for (int i = 0; i < exportSections.size(); i++) {
            sb.append("  ").append(exportSections.get(i).dump(i)).append("\n");
        }

        sb.append("Start: ").append(null == startFunctionIndex ? "none" : startFunctionIndex.unsigned().toString()).append("\n");

        sb.append("Element[").append(elementSections.size()).append("]:").append("\n");
        for (int i = 0; i < elementSections.size(); i++) {
            sb.append("  ").append(elementSections.get(i).dump((int)(importTableCount + i)).replace("\n", "\n  ")).append("\n");
        }

        sb.append("DataCount: ").append(null == dataCountIndex ? "none" : dataCountIndex.unsigned().toString()).append("\n");

        sb.append("Code[").append(codeSections.size()).append("]:").append("\n");
        for (int i = 0; i < codeSections.size(); i++) {
            sb.append("  ").append(codeSections.get(i).dump((int)(importFunctionCount + i))).append("\n");
        }

        sb.append("Data[").append(dataSections.size()).append("]:").append("\n");
        for (int i = 0; i < dataSections.size(); i++) {
            sb.append("  ").append(dataSections.get(i).dump((int)(importMemoryCount + i))).append("\n");
        }

        sb.append("Custom[").append(customSections.size()).append("]:").append("\n");
        for (int i = 0; i < customSections.size(); i++) {
            sb.append("  ").append(customSections.get(i).dump(i)).append("\n");
        }

        return sb.toString();
    }

    public FunctionType getBlockType(BlockType blockType) {
        if (null != blockType.type) {
            switch (blockType.type.value()) {
                case 0x40: // EMPTY
                    return new FunctionType(FunctionTypeTag.BLOCK_TYPE, new ValueType[0], new ValueType[0]);
                case 0x7F: // I32
                    return new FunctionType(FunctionTypeTag.BLOCK_TYPE, new ValueType[0], new ValueType[]{ValueType.I32});
                case 0x7E: // I64
                    return new FunctionType(FunctionTypeTag.BLOCK_TYPE, new ValueType[0], new ValueType[]{ValueType.I64});
//                case 0x7D: // F32
//                    return new FunctionType(FunctionTypeTag.BLOCK_TYPE, new ValueType[0], new ValueType[]{ValueType.F32});
//                case 0x7C: // F64
//                    return new FunctionType(FunctionTypeTag.BLOCK_TYPE, new ValueType[0], new ValueType[]{ValueType.F64});

                case 0x70: // FUNCTION_REFERENCE
                    return new FunctionType(FunctionTypeTag.BLOCK_TYPE, new ValueType[0], new ValueType[]{ValueType.FUNCTION_REFERENCE});
                case 0x6F: // EXTERN_REFERENCE
                    return new FunctionType(FunctionTypeTag.BLOCK_TYPE, new ValueType[0], new ValueType[]{ValueType.EXTERN_REFERENCE});
                default:
                    throw new ExecutionException("what a tag: " + blockType.type.value());
            }
        }
        return typeSections.get(blockType.s33.signed().intValue());
    }

}
