package expanse.vm.wasm.core;

import expanse.vm.wasm.error.execute.ExecutionException;
import expanse.vm.wasm.model.index.DataCountIndex;
import expanse.vm.wasm.model.index.FunctionIndex;
import expanse.vm.wasm.model.index.TypeIndex;
import expanse.vm.wasm.model.section.*;
import expanse.vm.wasm.model.tag.FunctionTypeTag;
import expanse.vm.wasm.model.type.BlockType;
import expanse.vm.wasm.model.type.ValueType;
import expanse.vm.wasm.util.ValidationSlice;
import might.vm.wasm.model.section.*;

import java.util.HashSet;
import java.util.Set;


public class ModuleInfo {

    public Magic magic;         // 魔数
    public Version version;     // 版本

    public final ValidationSlice<CustomSection> customSections = new ValidationSlice<>();     //  0 自定义段
    public ValidationSlice<FunctionType> typeSections = new ValidationSlice<>();        //  1 类型段 函数签名
    public ValidationSlice<ImportSection> importSections = new ValidationSlice<>();     //  2 导入 导入函数部分指向类型段的函数签名
    public ValidationSlice<TypeIndex> functionSections = new ValidationSlice<>();       //  3 函数段 指向类型段函数索引
    public ValidationSlice<TableType> tableSections = new ValidationSlice<>();          //  4 表
    public ValidationSlice<MemoryType> memorySections = new ValidationSlice<>();        //  5 内存
    public ValidationSlice<GlobalSection> globalSections = new ValidationSlice<>();     //  6 全局
    public ValidationSlice<ExportSection> exportSections = new ValidationSlice<>();     //  7 导出
    public FunctionIndex startFunctionIndex;                                  //  8 起始函数索引 uint32 应当是函数段的索引
    public ValidationSlice<ElementSection> elementSections = new ValidationSlice<>();   //  9 元素
    public DataCountIndex dataCountIndex;                                     // 12 数据计数段 memory.init data.drop 需要数据
    public ValidationSlice<CodeSection> codeSections = new ValidationSlice<>();         // 10 代码
    public ValidationSlice<DataSection> dataSections = new ValidationSlice<>();         // 11 数据

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
