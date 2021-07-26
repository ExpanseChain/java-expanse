package might.vm.wasm.core2.instruction.control;

import might.vm.wasm.core.error.Assertions;
import might.vm.wasm.core.error.WasmException;
import might.vm.wasm.core2.instruction.Instruction;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.core2.model.Dump;
import might.vm.wasm.core2.model.Local;
import might.vm.wasm.core2.model.index.FunctionIndex;
import might.vm.wasm.core2.model.section.CodeSection;
import might.vm.wasm.core2.numeric.U32;
import might.vm.wasm.core2.numeric.U64;
import might.vm.wasm.core2.numeric.USize;
import might.vm.wasm.core2.structure.Function;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class Call implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readFunctionIndex();
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, FunctionIndex.class);

        FunctionIndex index = ((FunctionIndex) args);

        Function function = mi.getFunction(index);

        callFunction(mi, function);
    }

    public void callFunction(ModuleInstance mi, Function function) {
        if (!function.isInternal()) {
            // 有本地函数内容，对于模块来说是外部的
            callExternalFunction(mi, function);
        } else {
            callInternalFunction(mi, function);
        }
    }

    private void callInternalFunction(ModuleInstance mi, Function function) {
        CodeSection code = function.getCodeSection();
        mi.enterBlock(Instruction.CALL, function.type(), code.expression);

        // 分配本地变量
        for (int i = 0; i < code.locals.length; i++) {
            Local local = code.locals[i];
            switch (local.type.value()) {
                case 0x7F: for (int j = 0; j < local.n.intValue(); j++) { mi.pushU32(U32.valueOf(0)); } break;
                case 0x7E: for (int j = 0; j < local.n.intValue(); j++) { mi.pushU64(U64.valueOf(0)); } break;
//            case 0x7D: return F32;
//            case 0x7C: return F64;
                case 0x70: for (int j = 0; j < local.n.intValue(); j++) { mi.pushU32(U32.valueOf(0)); } break;
                case 0x6F: for (int j = 0; j < local.n.intValue(); j++) { mi.pushU32(U32.valueOf(0)); } break;
                default:
                    throw new WasmException("what a type?");
            }
        }
    }

    private void callExternalFunction(ModuleInstance mi, Function function) {
        USize[] args = mi.popUSizes(function.type().parameters.length);
        USize[] results = function.call(args);
        mi.pushUSizes(results);
    }

}
