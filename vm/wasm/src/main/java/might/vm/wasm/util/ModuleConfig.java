package might.vm.wasm.util;

import might.common.numeric.I16;

public interface ModuleConfig {

    I16 getMinMainVersion();
    I16 getMaxMainVersion();

    I16 getMinSubVersion();
    I16 getMaxSubVersion();




}
