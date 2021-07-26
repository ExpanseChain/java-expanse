package might.vm.wasm.core;

import might.vm.wasm.instruction.Instruction;
import might.vm.wasm.util.Slice;

public class ControlStack {

    public final Slice<ControlFrame> frames = new Slice<>();

    public void push(ControlFrame frame) {
        frames.append(frame);
    }

    public ControlFrame pop() {
        return frames.remove(frames.size() - 1);
    }

    public int depth() {
        return frames.size();
    }

    public ControlFrame top() {
        return frames.get(frames.size() - 1);
    }

    public ControlFrame topCallFrame() {
        for (int i = frames.size() - 1; 0 <= i; i--) {
            if (frames.get(i).instruction == Instruction.CALL) {
                frames.get(i).setDepth(frames.size() - 1 - i); // 返回距离顶部的距离
                return frames.get(i);
            }
        }
        return null;
    }

    public void clear() {
        this.frames.clear();
    }

}
