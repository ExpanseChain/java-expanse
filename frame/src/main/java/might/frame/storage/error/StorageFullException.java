package might.frame.storage.error;

/**
 * 存储空间已满 不能继续存储
 */
public class StorageFullException extends StorageException {
    public StorageFullException(String message) {
        super(message);
    }
}
