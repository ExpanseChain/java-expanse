package expanse.vm.wasm.instance;

import expanse.common.numeric.ISize;
import expanse.vm.wasm.error.WasmException;
import expanse.vm.wasm.instruction.Action;
import expanse.vm.wasm.instruction.Instruction;
import expanse.vm.wasm.core.ModuleConfig;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.error.execute.OutOfGasException;

import java.util.Map;

public class GasModule extends Module {

    private final Map<Instruction, Long> gasMapping; // 指令gas限制
    private long gasLimit; // 当前执行最大gas限制
    private long gasCount; // 当前指令执行gas统计


    private GasModule(ModuleInfo moduleInfo, ModuleConfig config, Map<Instruction, Long> gasMapping) {
        super(moduleInfo, config);
        this.gasMapping = gasMapping;
        Assertions.requireNonNull(gasMapping);
    }

    public GasModule limit(long gasLimit) {
        this.gasLimit = gasLimit;
        this.gasCount = 0;
        return this;
    }

    public long getGasCount() {
        return gasCount;
    }

    @Override
    public ISize[] invoke(String name, ISize... args) {
        if (null == gasMapping) {
            throw new WasmException("no gas mapping");
        }
        return super.invoke(name, args);
    }


    @Override
    public void executeAction(Action action) {
//        System.out.println(action.getInstruction().name + " " + (null == action.getArgs() ? "" : action.getArgs().dump()));
        if (0 < gasLimit) {
            checkGas(action.getInstruction());
        }
        action.getInstruction().operate(this, action.getArgs());
    }

    private void checkGas(Instruction instruction) {
        long gas = gasMapping.get(instruction);
//        System.out.printf("%s\t\t gas: %d  limit: %d now: %d%n",
//                instruction.name, gas, gasLimit, gasCount);
        gasCount += gas;
        if (gasLimit < gasCount) {
            throw new OutOfGasException(String.format("gas limit(%d) < gas count(%d)", gasLimit, gasCount));
        }
    }

    public static GasModule newModule(ModuleInfo info, ModuleConfig config, Map<Instruction, Long> gasMapping) {
        return new GasModule(info, config, gasMapping);
    }

}
