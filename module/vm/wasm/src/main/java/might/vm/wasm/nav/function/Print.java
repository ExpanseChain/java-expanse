package might.vm.wasm.nav.function;

import might.common.numeric.ISize;
import might.vm.wasm.core.structure.Function;
import might.vm.wasm.model.section.FunctionType;
import might.vm.wasm.model.tag.FunctionTypeTag;
import might.vm.wasm.model.type.ValueType;

class Print {

    static class PrintChar implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I32 }, new ValueType[]{});
        }

        @Override
        public ISize[] call(ISize... args) {
            int v = args[0].signed().intValue();
            System.out.print(new String(new byte[]{(byte) v}));
            return new ISize[0];
        }
    }

    static class PrintI64 implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I64 }, new ValueType[]{});
        }

        @Override
        public ISize[] call(ISize... args) {
            System.out.print(args[0].unsigned().longValue());
            return new ISize[0];
        }
    }

    static class PrintI32 implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I32 }, new ValueType[]{});
        }

        @Override
        public ISize[] call(ISize... args) {
            System.out.print(args[0].signed().intValue());
            return new ISize[0];
        }
    }

}
