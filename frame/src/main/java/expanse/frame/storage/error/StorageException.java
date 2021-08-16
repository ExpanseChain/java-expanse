package expanse.frame.storage.error;

import expanse.frame.error.BlockChainException;

/**
 * 存储功能相关
 */
public class StorageException extends BlockChainException {
    public StorageException(String message) {
        super(message);
    }
}
