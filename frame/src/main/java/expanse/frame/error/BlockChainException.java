package expanse.frame.error;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一的异常
 */
@Slf4j
public class BlockChainException extends RuntimeException {
    public BlockChainException(String message) {
        super(message);
        log.error(message);
    }
}
