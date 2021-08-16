package might.frame.storage.error;

/**
 * 存储key不支持 null 和 长度为0的字节数组
 */
public class KeyNotSupportException extends StorageException {
    public KeyNotSupportException(String message) {
        super(message);
    }
}
