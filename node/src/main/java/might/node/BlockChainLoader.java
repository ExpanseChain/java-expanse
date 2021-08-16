package might.node;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import might.frame.BlockChain;
import might.node.error.WrongArgsException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@Slf4j
public class BlockChainLoader {

    // 配置 启动的区块链对象
    private static final String blockChainClass = "might.MightBlockChain";
    // genesis文件配置
    private String genesisFileName = "genesis.yml";
    // 本次运行是否为初始化创世块
    private boolean initGenesis = false;
    // 数据位置
    private String dataDir = "./data/";
    // 启动文件配置
    private String configFileName = "config.yml";

    public void loadArgs(String [] args) {
        for (String arg : args) {
            if (arg.startsWith("--genesis=")) {
                genesisFileName = arg.replaceFirst("--genesis=", "");
                initGenesis = true;
            } else if (arg.startsWith("--data=")) {
                dataDir = arg.replaceFirst("--data=", "");
                ;
            } else if (arg.startsWith("--config=")) {
                configFileName = arg.replaceFirst("--config=", "");
                ;
            } else {
                throw new WrongArgsException(String.format("confuse arg: %s.", arg));
            }
        }

        if (initGenesis) {
            log.info("genesis file: {}", genesisFileName);
        } else {
            log.info("config file: {}", configFileName);
        }
        log.info("data dir: {}", dataDir);
        checkDir(dataDir);
        if (!dataDir.endsWith(File.separator)) {
            dataDir = dataDir + File.separator;
        }
    }

    private static void checkDir(String dir) {
        File file = new File(dir);
        if (file.exists() && !file.isDirectory()) {
            throw new WrongArgsException(String.format("dir %s is file.", dir));
        }
        if (!file.exists() && !file.mkdirs()) {
            throw new WrongArgsException(String.format("create dir %s failed.", dir));
        }
    }

    private static JSONObject getConfig(String fileName) {
        File file = new File(fileName);

        if (!file.exists() || !file.isFile()) {
            throw new WrongArgsException(String.format("can not find file: %s", fileName));
        }

        Map<?,?> map;
        try {
            if (file.isAbsolute()) {
                map = new Yaml().load(new FileInputStream(file));
            } else {
                map = new Yaml()
                        .load(BlockChainLoader.class.getClassLoader().getResourceAsStream(fileName));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            String message = String.format("load file %s failed.", fileName);
            throw new WrongArgsException(message);
        }

        if (null == map) { return new JSONObject(); }

        return JSON.parseObject(JSON.toJSONString(map));
    }

    public BlockChain getBlockChain() {
        JSONObject config = getConfig(configFileName);

        String blockChainClass = config.getString("blockChainClass");
        if (null == blockChainClass) {
            blockChainClass = BlockChainLoader.blockChainClass;
        }
        Class<?> c;
        try {
            c = NodeMain.class.getClassLoader().loadClass(blockChainClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            String message = String.format("can not find class: %s", blockChainClass);
            throw new WrongArgsException(message);
        }
        Object o;
        try {
            o = c.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            String message = String.format("can not instance class: %s", blockChainClass);
            throw new WrongArgsException(message);
        }
        if (!(o instanceof BlockChain)) {
            throw new WrongArgsException(String.format("class %s must extend BlockChain", blockChainClass));
        }

        BlockChain blockChain =  (BlockChain) o;

        if (initGenesis) {
            blockChain.initGenesis(getConfig(genesisFileName), dataDir);
            log.info("init genesis block successful.");
        } else {
            blockChain.initConfig(config, dataDir);
        }

        return blockChain;
    }

    public boolean isInitGenesis() {
        return initGenesis;
    }

}
