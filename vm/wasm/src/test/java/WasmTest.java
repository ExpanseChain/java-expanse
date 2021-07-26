import might.vm.wasm.core2.instance.Module;
import might.vm.wasm.core2.structure.ModuleInfo;
import might.vm.wasm.core2.structure.WasmReader;
import org.junit.jupiter.api.Test;
import util.FileReader;

public class WasmTest {
    
    public static ModuleInfo readByName(String name) {
        System.out.println("read file: " + name);
        WasmReader reader = new WasmReader(FileReader.readByName(name));
        return reader.readModuleInfo();
    }
    
    @Test
    public void main() {
        String[] names = {
//                "ch01_hw.wasm",
//                "ch01_hw2.wasm",
//                "hw_rust.wasm",
//
////                "ch03_eg1_num.wasm",
//                "ch03_eg1_num2.wasm",
//                "ch03_eg2_var.wasm",
//                "ch03_eg3_mem.wasm",
//                "ch03_eg4_block.wasm",
//                "ch03_eg5_br.wasm",
//                "ch03_eg6_call.wasm",
//
//                "ch05_cz.wasm",
////                "ch05_num.wasm",
//                "ch05_num2.wasm",
//                "ch05_param.wasm",

//                "ch06_mem.wasm",
//                "ch06_mem2.wasm",

//                "ch07_fib.wasm",
////                "ch07_global.wasm",
//                "ch07_global2.wasm",
//                "ch07_local.wasm",
//                "ch07_max.wasm",
//                "ch07_sum.wasm",

//                "ch08_cmp.wasm",
//                "ch08_fac.wasm",
//                "ch08_sum.wasm",
//                "ch08_test.wasm",
//                "ch08_eg1_labels.wasm",
//                "ch08_eg2_nested_labels.wasm",
//                "ch08_eg3_label_names.wasm",
//                "ch08_eg4_add.wasm",
//                "ch08_eg5_calc.wasm",
//                "ch08_eg6_br.wasm",
//                "ch08_eg7_br_if.wasm",
//                "ch08_eg8_br_table.wasm",
//                "ch08_eg9_return.wasm",

//                "ch09_calc.wasm",

        };
        for (String n : names) {
            System.out.println("=============== " + n + " ===============");
            ModuleInfo moduleInfo = readByName(n);
            System.out.print(moduleInfo.dump());
        }

//        VirtualMachine.execStartFunction(readByName("ch05_cz.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch05_num2.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch05_param.wasm"));

//        VirtualMachine.execStartFunction(readByName("ch06_mem2.wasm"));

//        VirtualMachine.execStartFunction(readByName("ch07_fib.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch07_global2.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch07_local.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch07_max.wasm"));

//        VirtualMachine.execStartFunction(readByName("ch08_cmp.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_fac.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_sum.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_test.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_eg1_labels.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_eg2_nested_labels.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_eg3_label_names.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_eg4_add.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_eg5_calc.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_eg6_br.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_eg7_br_if.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_eg8_br_table.wasm"));
//        VirtualMachine.execStartFunction(readByName("ch08_eg9_return.wasm"));

//        VirtualMachine.execStartFunction(readByName("ch01_hw2.wasm"));

//        VirtualMachine.execStartFunction(readByName("ch09_calc.wasm"));


//        Module.newModule(readByName("ch01_hw2.wasm"));
//
//        Module.newModule(readByName("ch05_cz.wasm"));
//        Module.newModule(readByName("ch05_num2.wasm"));
//        Module.newModule(readByName("ch05_param.wasm"));
//
//        Module.newModule(readByName("ch06_mem2.wasm"));
//
//        Module.newModule(readByName("ch07_fib.wasm"));
//        Module.newModule(readByName("ch07_global2.wasm"));
//        Module.newModule(readByName("ch07_local.wasm"));
//        Module.newModule(readByName("ch07_max.wasm"));
//
//        Module.newModule(readByName("ch08_cmp.wasm"));
//        Module.newModule(readByName("ch08_fac.wasm"));
//        Module.newModule(readByName("ch08_sum.wasm"));
//        Module.newModule(readByName("ch08_test.wasm"));
//        Module.newModule(readByName("ch08_eg1_labels.wasm"));
//        Module.newModule(readByName("ch08_eg2_nested_labels.wasm"));
//        Module.newModule(readByName("ch08_eg3_label_names.wasm"));
//        Module.newModule(readByName("ch08_eg4_add.wasm"));
//        Module.newModule(readByName("ch08_eg5_calc.wasm"));
//        Module.newModule(readByName("ch08_eg6_br.wasm"));
//        Module.newModule(readByName("ch08_eg7_br_if.wasm"));
//        Module.newModule(readByName("ch08_eg8_br_table.wasm"));
//        Module.newModule(readByName("ch08_eg9_return.wasm"));
//
//        Module.newModule(readByName("ch09_calc.wasm"));

//        Module.newModule(readByName("ch12_ctrl.wasm")).invoke("print_even", U32.valueOf(90));
        Module.newModule(readByName("ch13_prime.wasm")).invoke("main");

    }

}
