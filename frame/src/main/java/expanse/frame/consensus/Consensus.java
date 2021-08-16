package expanse.frame.consensus;

/**
 * 共识接口
 * 任务是：
 * 给出上一个区块信息
 * 0. 判断收到的区块是否符合规则（POW或POS）
 * 1. 获取有权限出块的账户 (POW 所有账户都可以 POS 要看规则)
 * 2. 用有权限的账户计算下一个候选区块
 * 3. 按照规则 找到可用的 (POW要计算随机数符合难度，POS要签名完毕)
 */
public interface Consensus {



}
