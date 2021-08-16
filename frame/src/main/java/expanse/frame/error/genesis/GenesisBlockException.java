package expanse.frame.error.genesis;

import expanse.frame.error.BlockChainException;

public class GenesisBlockException extends BlockChainException {
    public GenesisBlockException(String message) {
        super(message);
    }
}
