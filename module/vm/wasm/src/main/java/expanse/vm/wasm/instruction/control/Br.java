package expanse.vm.wasm.instruction.control;

import expanse.vm.wasm.core.WasmReader;
import expanse.vm.wasm.instruction.Operate;
import expanse.vm.wasm.core.ControlFrame;
import expanse.vm.wasm.core.ModuleInfo;
import expanse.vm.wasm.core.structure.ModuleInstance;
import expanse.vm.wasm.error.Assertions;
import expanse.vm.wasm.instruction.Instruction;
import expanse.vm.wasm.model.Dump;
import expanse.vm.wasm.model.index.LabelIndex;

public class Br implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readLabelIndex();
    }

    @Override
    public void validate(ModuleInfo info, Dump args, int parameters, long locals) {
        Assertions.requireTrue(null != args);
        Assertions.requireTrue(args instanceof LabelIndex);
//        LabelIndex i = (LabelIndex) args;
        // 退出多少个块
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, LabelIndex.class);

        int index = ((LabelIndex) args).unsigned().intValue();

        for (int i = 0; i < index; i++) {
            mi.popFrame();
        }

        ControlFrame frame = mi.topFrame();

        if (frame.instruction != Instruction.LOOP) {
            mi.exitBlock();
        } else {
            mi.resetBlock(frame);
            frame.pc = 0;
        }
    }
}
