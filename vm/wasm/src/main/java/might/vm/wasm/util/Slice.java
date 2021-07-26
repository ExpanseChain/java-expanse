package might.vm.wasm.util;

import might.vm.wasm.error.module.IndexOutOfBoundsException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

public class Slice<T> implements Iterable<T> {

    private static final int MAX_CAPACITY = 1024 * 1024; // 最大容量

    private final ArrayList<T> data;

    public Slice() { data = new ArrayList<>(); }

    public Slice(int capacity) {
        if (MAX_CAPACITY < capacity) {
            throw new IndexOutOfBoundsException("capacity limit: " + MAX_CAPACITY);
        }
        if (capacity < 0) {
            throw new IndexOutOfBoundsException("capacity can not be " + capacity);
        }
        data = new ArrayList<>(capacity);
    }

    private void checkIndex(int index) {
        if (data.size() <= index) {
            throw new IndexOutOfBoundsException("try get item which is not exist: " + index);
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("try get item where index is negation: " + index);
        }
    }

    public int size() { return data.size(); }

    @Override
    public Iterator<T> iterator() { return data.stream().iterator(); }

    public Stream<T> stream() { return data.stream(); }

    public void append(T t) {
        if (data.size() == MAX_CAPACITY) {
            throw new IndexOutOfBoundsException("capacity limit: " + MAX_CAPACITY);
        }
        data.add(t);
    }

    public T get(int index) {
        checkIndex(index);
        return data.get(index);
    }



}
