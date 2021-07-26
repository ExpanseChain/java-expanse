package might.vm.wasm.instance;

import might.common.numeric.I32;
import might.vm.wasm.model.section.TableType;
import might.vm.wasm.core2.numeric.U32;
import might.vm.wasm.core.structure.Function;
import might.vm.wasm.core.structure.Table;

public class TableInstance implements Table {

    public final TableType type;

    private Function[] elements;

    public TableInstance(TableType type) {
        this.type = type;
        this.elements = new FunctionInstance[type.limits.getMin().unsigned().intValue()];
    }


    @Override
    public TableType type() {
        return type;
    }

    @Override
    public U32 size() {
        return U32.valueOf(elements.length);
    }

    @Override
    public void grow(U32 grow) {
        int g = grow.intValue();

        if (g <= 0) {
            throw new RuntimeException("wrong grow: " + grow);
        }

        int wanna = elements.length + g;

        if (!type.limits.isValid(I32.valueOf(wanna))) {
            throw new RuntimeException("too large for: " + type.limits);
        }

        Function[] es = new Function[wanna];

        System.arraycopy(elements, 0, es, 0, elements.length);

        elements = es;
    }

    @Override
    public Function getElement(U32 index) {
        int i = index.intValue();
        if (elements.length <= i) {
            throw new RuntimeException("too large");
        }
        return elements[i];
    }

    @Override
    public void setElement(U32 index, Function element) {
        int i = index.intValue();
        if (elements.length <= i) {
            throw new RuntimeException("too large");
        }
        elements[i] = element;
    }

}
