package might.vm.wasm.nav.function;

import might.common.numeric.I64;
import might.common.numeric.ISize;
import might.vm.wasm.core.structure.Function;
import might.vm.wasm.model.section.FunctionType;
import might.vm.wasm.model.tag.FunctionTypeTag;
import might.vm.wasm.model.type.ValueType;

class Assert {

    static class AssertTrue implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I64 }, new ValueType[]{});
        }
        @Override
        public ISize[] call(ISize... args) {
            if (!args[0].booleanValue()) {
                // 0 则为 false
                throw new RuntimeException("not equals: " + args[0] + " == 1");
            }
            return new ISize[0];
        }
    }

    static class AssertFalse implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I64 }, new ValueType[]{});
        }
        @Override
        public ISize[] call(ISize... args) {
            if (args[0].booleanValue()) {
                // 0 则为 true
                throw new RuntimeException("not equals: " + args[0] + " == 0");
            }
            return new ISize[0];
        }
    }

    static class AssertEqualInt implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I64, ValueType.I64 }, new ValueType[]{});
        }
        @Override
        public ISize[] call(ISize... args) {
            if (args[0].signed().compareTo(args[1].signed()) != 0) {
                // 0 则为 true
                throw new RuntimeException("not equals: " + args[0] + " == " + args[1]);
            }
            return new ISize[0];
        }
    }

    static class AssertEqualLong implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I64, ValueType.I64 }, new ValueType[]{});
        }
        @Override
        public ISize[] call(ISize... args) {
            if (args[0].signed().compareTo(args[1].signed()) != 0) {
                // 0 则为 true
                throw new RuntimeException("not equals: " + args[0] + " == " + args[1]);
            }
            return new I64[0];
        }
    }

}
