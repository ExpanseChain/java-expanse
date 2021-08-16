package might.frame.storage;

import might.frame.storage.error.AlreadyClosedException;
import might.frame.storage.error.KeyNotSupportException;

public abstract class SimpleStorage implements Storage {

    private boolean closed = false;

    public byte[] read(byte[] key) {
        if (closed) {
            throw new AlreadyClosedException("storage already closed");
        }
        if (null == key) {
            throw new KeyNotSupportException("key can not be null");
        }
        return read0(key);
    }

    protected abstract byte[] read0(byte[] key);

    public void write(byte[] key, byte[] data) {
        if (closed) {
            throw new AlreadyClosedException("storage already closed");
        }
        if (null == key) {
            throw new KeyNotSupportException("key can not be null");
        }
        write0(key, data);
    }

    protected abstract void write0(byte[] key, byte[] data);

    /**
     * 关闭
     */
    public void close() {
        closed = true;
        close0();
    }

    protected abstract void close0();

}
