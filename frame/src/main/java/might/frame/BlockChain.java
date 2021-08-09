package might.frame;

/**
 * 每一个BlockChain实例都代表着一条链
 * 所有功能均出自BlockChain实例
 */
public interface BlockChain {


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
