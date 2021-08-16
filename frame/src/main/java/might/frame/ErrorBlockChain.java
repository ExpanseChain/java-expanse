package might.frame;

import com.alibaba.fastjson.JSONObject;
import might.frame.error.BlockChainException;

public class ErrorBlockChain implements BlockChain {
    @Override
    public void initGenesis(JSONObject genesisConfig, String dataDir) {
        throw new BlockChainException("this method should be override");
    }

    @Override
    public void initConfig(JSONObject config, String dataDir) {
        throw new BlockChainException("this method should be override");
    }

    @Override
    public void run() {
        throw new BlockChainException("this method should be override");
    }

    @Override
    public void synchro(boolean run) {
        throw new BlockChainException("this method should be override");
    }

    @Override
    public void mine(boolean run) {
        throw new BlockChainException("this method should be override");
    }

    @Override
    public String cli(String args) {
        throw new BlockChainException("this method should be override");
    }
}
