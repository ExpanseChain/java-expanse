package expanse.vm.wasm.instance;

import expanse.common.numeric.I32;
import expanse.vm.wasm.core.structure.Function;
import expanse.vm.wasm.core.structure.Table;
import expanse.vm.wasm.error.execute.ExecutionException;
import expanse.vm.wasm.error.module.ModuleException;
import expanse.vm.wasm.model.section.TableType;
import expanse.vm.wasm.util.Slice;

public class TableInstance implements Table {

    public final TableType type;

    private final Slice<Function> elements;

    public TableInstance(TableType type) {
        this.type = type;
        this.elements = new Slice<>(type.limits.getMin().unsigned().intValue());
        this.elements.set(type.limits.getMin().unsigned().intValue() - 1, null);
    }


    @Override
    public TableType type() {
        return type;
    }

    @Override
    public I32 size() {
        return I32.valueOf(elements.size());
    }

    @Override
    public void grow(I32 grow) {
        int g = grow.unsigned().intValue();

        if (g <= 0) {
            throw new ModuleException("wrong grow: " + grow);
        }

        int wanna = elements.size() + g;

        if (!type.limits.isValid(I32.valueOf(wanna))) {
            throw new ModuleException("too large for: " + type.limits);
        }

        elements.set(wanna - 1, null);
    }

    @Override
    public Function getElement(I32 index) {
        int i = index.unsigned().intValue();
        Slice.checkArrayIndex(i);
        if (elements.size() <= i) {
            throw new ExecutionException("wrong index: " + i);
        }
        return elements.get(i);
    }

    @Override
    public void setElement(I32 index, Function element) {
        int i = index.unsigned().intValue();
        Slice.checkArrayIndex(i);
        if (elements.size() <= i) {
            throw new ExecutionException("wrong index: " + i);
        }
        elements.set(i, element);
    }

}
