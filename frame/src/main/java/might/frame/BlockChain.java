package might.frame;

import com.alibaba.fastjson.JSONObject;

/**
 * 每一个BlockChain实例都代表着一条链
 * 所有功能均出自BlockChain实例
 */
public interface BlockChain {

    /**
     * 初始化创世块
     * @param genesisConfig 创世块配置
     * @param dataDir 数据目录
     */
    void initGenesis(JSONObject genesisConfig, String dataDir);

    /**
     * 初始化配置
     * @param config 启动配置信息
     * @param dataDir 数据目录
     */
    void initConfig(JSONObject config, String dataDir);

    /**
     * 加载配置文件后 运行
     */
    void run();

    // =================== 下面是区块链的功能 ===================

    /**
     * 随时同步区块链信息
     * @param run 启动或关闭
     */
    void synchro(boolean run);

    /**
     * 进行挖矿
     * @param run 启动或关闭
     */
    void mine(boolean run);


    /**
     * 与客户端交互 TODO 待修正 传入什么参数好？
     */
    String cli(String args);



}
