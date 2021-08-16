package might.mode.storage.leveldb;

import might.frame.storage.SimpleStorage;
import might.frame.storage.error.StorageException;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.IOException;

public class LevelDBStorage extends SimpleStorage {

    private final String path;
    private final DB db;

    public LevelDBStorage(String path) {
        this.path = path;
        DBFactory factory = new Iq80DBFactory();
        File file = new File(path);
        try {
            db = factory.open(file, new Options());
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("can not init leveldb by path: " + path);
        }
    }

    @Override
    protected byte[] read0(byte[] key) {
        return db.get(key);
    }

    @Override
    protected void write0(byte[] key, byte[] data) {
        db.put(key, data);
    }

    @Override
    public void clear() {
        for (java.util.Map.Entry<byte[], byte[]> entry : db) {
            db.delete(entry.getKey());
        }
    }

    @Override
    protected void close0() {
        if (db != null) {
            try {
                db.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new StorageException("can not close leveldb: " + path);
            }
        }
    }

    @Override
    protected boolean has0(byte[] key) {
        return null != db.get(key);
    }

}
