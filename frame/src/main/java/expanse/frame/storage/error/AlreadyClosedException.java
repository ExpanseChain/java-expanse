package expanse.frame.storage.error;

public class AlreadyClosedException extends StorageException {
    public AlreadyClosedException(String message) {
        super(message);
    }
}
