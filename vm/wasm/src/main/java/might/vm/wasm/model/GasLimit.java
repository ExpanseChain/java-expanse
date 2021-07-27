package might.vm.wasm.model;

public class GasLimit {

    public final long gasLimit;

    public long gasCount;

    private GasLimit(long gasLimit) {
        this.gasLimit = gasLimit;
    }

    public static GasLimit of(long limit) {
        return new GasLimit(limit);
    }


}
