package expanse.vm.wasm.model.section;

import expanse.common.numeric.I32;
import expanse.common.numeric.I64;
import expanse.common.numeric.ISize;
import expanse.vm.wasm.model.type.ValueType;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.decode.DecodeException;
import expanse.vm.wasm.error.execute.ExecutionException;
import expanse.vm.wasm.model.Validate;
import expanse.vm.wasm.model.tag.FunctionTypeTag;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FunctionType implements Validate {

    public final FunctionTypeTag tag;       // 函数标识
    public final ValueType[] parameters;    // 参数列表
    public final ValueType[] results;       // 结果列表

    public FunctionType(FunctionTypeTag tag, ValueType[] parameters, ValueType[] results) {
        this.tag = tag;
        this.parameters = parameters;
        this.results = results;
    }

    @Override
    public String toString() {
        return "FunctionType{" +
                "tag=" + tag +
                ", parameters=" + Arrays.toString(parameters) +
                ", results=" + Arrays.toString(results) +
                '}';
    }

    public String dump(int index) {
        return "type[" + index + "]: (" +
                Arrays.stream(parameters).map(ValueType::name)
                        .collect(Collectors.joining(",")) +
                ")->(" +
                Arrays.stream(results).map(ValueType::name)
                        .collect(Collectors.joining(",")) +
                ")";
    }

    public boolean same(FunctionType other) {
        if (tag.value() != other.tag.value()) { return false; }
        if (parameters.length != other.parameters.length) { return false; }
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].value() != other.parameters[i].value()) {
                return false;
            }
        }
        if (results.length != other.results.length) { return false; }
        for (int i = 0; i < results.length; i++) {
            if (results[i].value() != other.results[i].value()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void validate(ModuleInfo info) {
        // 类型段 能构造成功就通过了
    }

    public void checkParameters(ISize[] args) {
        if (args.length != parameters.length) {
            throw new ExecutionException(String.format("args is mismatch. this function(%d) != args(%d)", parameters.length, args.length));
        }
        for (int i = 0; i < parameters.length; i++) {
            ValueType p = parameters[i];
            switch (p.value()) {
                case 0x7F: // I32;
                    if (!(args[i] instanceof I32)) {
                        throw new ExecutionException(String.format("args is mismatch. this function(%s) != args(%s)", Arrays.toString(parameters), Arrays.toString(args)));
                    }
                    break;
                case 0x7E: // I64;
                case 0x70: // FUNCTION_REFERENCE;
                case 0x6F: // EXTERN_REFERENCE;
                    if (!(args[i] instanceof I64)) {
                        throw new ExecutionException(String.format("args is mismatch. this function(%s) != args(%s)", Arrays.toString(parameters), Arrays.toString(args)));
                    }
                    break;
                default:
                    throw new DecodeException("what a type: " + p.value());
            }
        }
    }

}
