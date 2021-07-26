package might.vm.wasm.core2.instruction.control;

import might.vm.wasm.error.Assertions;
import might.vm.wasm.core2.instruction.Instruction;
import might.vm.wasm.core2.instruction.Operate;
import might.vm.wasm.model.Dump;
import might.vm.wasm.model.index.LabelIndex;
import might.vm.wasm.core2.structure.ControlFrame;
import might.vm.wasm.core2.structure.ModuleInstance;
import might.vm.wasm.core2.structure.WasmReader;

public class Br implements Operate {

    @Override
    public Dump read(WasmReader reader) {
        return reader.readLabelIndex();
    }

    @Override
    public void operate(ModuleInstance mi, Dump args) {
        Assertions.requireNonNull(args);
        Assertions.requireType(args, LabelIndex.class);

        int index = ((LabelIndex) args).intValue();

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
