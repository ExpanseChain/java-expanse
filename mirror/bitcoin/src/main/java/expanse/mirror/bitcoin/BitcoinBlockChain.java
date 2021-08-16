package expanse.mirror.bitcoin;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import expanse.common.util.HexUtil;
import expanse.frame.ErrorBlockChain;
import expanse.frame.error.genesis.GenesisBlockException;
import expanse.frame.storage.Storage;
import expanse.mode.storage.leveldb.LevelDBStorage;

import java.nio.charset.StandardCharsets;

@Slf4j
public class BitcoinBlockChain extends ErrorBlockChain {

    private static final byte[] GENESIS_BLOCK_HEADER = "genesis.block.header".getBytes(StandardCharsets.UTF_8);

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

        // 清除数据
        headerStorage.clear();
        transactionStorage.clear();

        // do other thing

    }

    @Override
    public void initConfig(JSONObject config, String dataDir) {
        initStorage(dataDir);

        byte[] genesisBlockHeader = configStorage.read(GENESIS_BLOCK_HEADER);
        if (null == genesisBlockHeader) {
            throw new GenesisBlockException("genesis block has not initial");
        }
        log.info("genesis block header -> 0x{}", HexUtil.toHex(genesisBlockHeader));

    }


}
