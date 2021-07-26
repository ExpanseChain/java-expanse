package might.vm.wasm.core2.nav.function;

import might.vm.wasm.model.section.FunctionType;
import might.vm.wasm.model.tag.FunctionTypeTag;
import might.vm.wasm.model.type.ValueType;
import might.vm.wasm.core2.numeric.USize;
import might.vm.wasm.core2.structure.Function;

class Print {

    static class PrintChar implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I32 }, new ValueType[]{});
        }

        @Override
        public USize[] call(USize... args) {
            int v = args[0].intValue();
            System.out.print(new String(new byte[]{(byte) v}));
            return new USize[0];
        }
    }

    static class PrintI64 implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I64 }, new ValueType[]{});
        }

        @Override
        public USize[] call(USize... args) {
            System.out.print(args[0].longValue());
            return new USize[0];
        }
    }

    static class PrintI32 implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I32 }, new ValueType[]{});
        }

        @Override
        public USize[] call(USize... args) {
            System.out.print(args[0].intValue());
            return new USize[0];
        }
    }

}
