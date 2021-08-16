package expanse.frame.storage.error;

/**
 * 写入数据太大 无法保存
 */
public class DataTooLargeException extends StorageException {
    public DataTooLargeException(String message) {
        super(message);
    }
}
