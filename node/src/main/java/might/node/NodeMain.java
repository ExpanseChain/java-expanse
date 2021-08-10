package might.node;

import lombok.extern.slf4j.Slf4j;
import might.frame.BlockChain;

@Slf4j
public class NodeMain {

    public static void main(String[] args) {
        BlockChainLoader loader = new BlockChainLoader();
        loader.loadArgs(args);
        BlockChain blockChain = loader.getBlockChain();
        if (!loader.isInitGenesis()) {
            blockChain.run();
        }
    }

}
