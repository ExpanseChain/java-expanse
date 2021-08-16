package might.mirror.bitcoin;

import com.alibaba.fastjson.JSONObject;
import might.frame.ErrorBlockChain;
import might.frame.storage.Storage;
import might.mode.storage.leveldb.LevelDBStorage;

public class BitcoinBlockChain extends ErrorBlockChain {

    private Storage configStorage;
    private Storage headerStorage;
    private Storage transactionStorage;

    private void initStorage(String dataDir) {
        configStorage = new LevelDBStorage(dataDir + "config");
        headerStorage = new LevelDBStorage(dataDir + "header");
        transactionStorage = new LevelDBStorage(dataDir + "transaction");
    }

    @Override
    public void initGenesis(JSONObject genesisConfig, String dataDir) {
        initStorage(dataDir);
        // do other thing
    }

    @Override
    public void initConfig(JSONObject config, String dataDir) {
        initStorage(dataDir);
        // do other thing
    }


}
