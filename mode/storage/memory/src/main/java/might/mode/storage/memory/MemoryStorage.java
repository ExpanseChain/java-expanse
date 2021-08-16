package might.mode.storage.memory;

import might.frame.storage.SimpleStorage;

import java.util.HashMap;

public class MemoryStorage extends SimpleStorage {

    private final HashMap<byte[], byte[]> map = new HashMap<>();

    @Override
    protected byte[] read0(byte[] key) {
        return map.get(key);
    }

    @Override
    protected void write0(byte[] key, byte[] data) {
        if (null == data) {
            map.remove(key);
        } else {
            map.put(key, data);
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected void close0() {
        // do nothing
    }

}
