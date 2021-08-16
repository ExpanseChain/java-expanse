package might.vm.wasm.util;

import expanse.common.numeric.I32;
import might.vm.wasm.error.module.IndexOutOfBoundsException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

public class Slice<T> implements Iterable<T> {

    private static final int MAX_CAPACITY = 1024 * 1024 * 1024; // 最大容量 10亿的数组也太夸张了

    protected final ArrayList<T> data;

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

    public T get(I32 index) {
        return get(index.unsigned().intValue());
    }

    public T remove(int index) {
        checkIndex(index);
        return data.remove(index);
    }

    public void set(int index, T value) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("try set item where index is negation: " + index);
        }
        if (MAX_CAPACITY - 1 < index) {
            throw new IndexOutOfBoundsException("capacity limit: " + MAX_CAPACITY);
        }
        while (data.size() - 1 < index) { data.add(null); }
        data.set(index, value);
    }

    public void clear() {
        data.clear();
    }


    public static int checkArrayIndex(int n) {
        if (n < 0 || MAX_CAPACITY < n) {
            throw new IndexOutOfBoundsException("the capacity limit of array: " + MAX_CAPACITY);
        }
        return n;
    }

}
