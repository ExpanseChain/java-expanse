package expanse.frame.storage;

/**
 * 存储功能
 * 区块链数据层难点在于数据无序，存储效率实在不高
 */
public interface Storage {

    /**
     * 读取数据 return null if not exist
     */
    byte[] read(byte[] key);

    /**
     * 存储数据
     */
    void write(byte[] key, byte[] data);

    /**
     * 删除数据
     */
    default byte[] remove(byte[] key) {
        byte[] data = read(key);
        write(key, null);
        return data;
    }

    /**
     * 删除数据
     */
    default void delete(byte[] key) {
        write(key, null);
    }

    /**
     * 清除所有存储
     */
    void clear();

    /**
     * 关闭 已经关闭的不能再操作
     */
    void close();

    /**
     * 是否包含
     */
    boolean has(byte[] key);

}
