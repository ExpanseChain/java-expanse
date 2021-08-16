package expanse.vm.wasm.model;

import expanse.common.numeric.I32;
import expanse.vm.wasm.model.tag.LimitsTag;

public class Limits {

    protected final LimitsTag tag;    // 0x00 只有min  如果是I0x01 还要读取max值
    protected final I32 min;          // 最小值
    protected final I32 max;          // 最大值

    public Limits(LimitsTag tag, I32 min, I32 max) {
        this.tag = tag;
        this.min = min;
        this.max = max;
    }

    public LimitsTag getTag() {
        return tag;
    }

    public I32 getMin() {
        return min;
    }

    public I32 getMax() {
        return max;
    }

    public boolean isValid(I32 wanna) {
        if (null == max) { return true; }
        return wanna.lessOrEqualsU(max); // 小于活等于最大限制
    }

    @Override
    public String toString() {
        return "Limits{" +
                "tag=" + tag +
                ", min=" + min +
                ", max=" + max +
                '}';
    }

    public String dump() {
        return "{min: " + min.unsigned().toString() + ", max: " + (null == max ? "0" : max.unsigned().toString()) + "}";
    }

    public boolean same(Limits o) {
        return tag == o.tag && min.equals(o.min) && ((null == max && null == o.max)||(null != max && max.equals(o.max)));
    }

}
