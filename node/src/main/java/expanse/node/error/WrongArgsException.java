package expanse.node.error;

import expanse.frame.error.BlockChainException;

public class WrongArgsException extends BlockChainException {
    public WrongArgsException(String message) {
        super(message);
    }
}
