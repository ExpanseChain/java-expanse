package might.node;

import lombok.extern.slf4j.Slf4j;
import might.node.error.WrongArgsException;

@Slf4j
public class NodeMain {

    // 配置 启动的区块链对象
    private static String blockChainClass = "might.MightBlockChain";
    // genesis文件配置
    private static String genesisFileName = "genesis.json";
    // 本次运行是否为初始化创世块
    private static boolean initGenesis = false;
    // 数据位置
    private static String dataDir = "./data/";
    // 启动文件配置
    private static String configFileName = "config.json";

    public static void main(String[] args) {
        if (initGenesis) {
            log.info("genesis file: {}", genesisFileName);
        } else {
            log.info("config file: {}", configFileName);
        }
        log.info("data dir: {}", dataDir);

//        JSONO
//
//        BlockChain blockChain =

    }

    private static void parseArgs(String [] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--genesis": genesisFileName = args[checkIndex(args, i)]; initGenesis = true; break;
                case "--data": dataDir = args[checkIndex(args, i)]; break;
                case "--config": configFileName = args[checkIndex(args, i)]; break;
                default:
                    throw new WrongArgsException(String.format("confuse arg: %s.", args[i]));
            }
        }
    }

    private static int checkIndex(String[] args, int i) {
        if (args.length <= i + 1) {
            throw new WrongArgsException(String.format("arg %s must has value.", args[i]));
        }
        return i + 1;
    }




}
