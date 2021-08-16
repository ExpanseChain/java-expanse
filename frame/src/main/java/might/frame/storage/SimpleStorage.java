package might.frame.storage;

import might.frame.storage.error.AlreadyClosedException;
import might.frame.storage.error.KeyNotSupportException;

public abstract class SimpleStorage implements Storage {

    private boolean closed = false;

    private void check(byte[] key) {
        if (closed) {
            throw new AlreadyClosedException("storage already closed");
        }
        if (null == key) {
            throw new KeyNotSupportException("key can not be null");
        }
    }

    public final byte[] read(byte[] key) {
        check(key);
        return read0(key);
    }

    protected abstract byte[] read0(byte[] key);

    public final void write(byte[] key, byte[] data) {
        check(key);
        write0(key, data);
    }

    protected abstract void write0(byte[] key, byte[] data);

    public final void close() {
        if (!closed) {
            closed = true;
            close0();
        }
    }

    protected abstract void close0();

    public final boolean has(byte[] key) {
        check(key);
        return has0(key);
    }

    protected abstract boolean has0(byte[] key);

}
