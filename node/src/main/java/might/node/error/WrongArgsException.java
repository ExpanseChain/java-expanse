package might.node.error;

import might.frame.error.BlockChainException;

public class WrongArgsException extends BlockChainException {
    public WrongArgsException(String message) {
        super(message);
    }
}
