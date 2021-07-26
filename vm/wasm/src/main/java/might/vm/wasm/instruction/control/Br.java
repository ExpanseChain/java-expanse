package might.vm.wasm.instruction.control;

import might.vm.wasm.core.ControlFrame;
import might.vm.wasm.core.ModuleInfo;
import might.vm.wasm.core.WasmReader;
import might.vm.wasm.core.structure.ModuleInstance;
import might.vm.wasm.error.Assertions;
import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.LabelIndex;

public class Br implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readLabelIndex();
    }

    @Override
    public void valid(ModuleInfo info, Dump args, int parameters, long locals) {
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
