package might.vm.wasm.nav.function;

import might.vm.wasm.model.section.FunctionType;
import might.vm.wasm.model.tag.FunctionTypeTag;
import might.vm.wasm.model.type.ValueType;
import might.vm.wasm.core2.numeric.U64;
import might.vm.wasm.core2.numeric.USize;
import might.vm.wasm.core.structure.Function;

class Assert {

    static class AssertTrue implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I64 }, new ValueType[]{});
        }
        @Override
        public USize[] call(USize... args) {
            if (args[0].longValue() == 0) {
                // 0 则为 false
                throw new RuntimeException("not equals: " + args[0] + " == 1");
            }
            return new USize[0];
        }
    }

    static class AssertFalse implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I64 }, new ValueType[]{});
        }
        @Override
        public USize[] call(USize... args) {
            if (args[0].longValue() != 0) {
                // 0 则为 true
                throw new RuntimeException("not equals: " + args[0] + " == 0");
            }
            return new USize[0];
        }
    }

    static class AssertEqualInt implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I64, ValueType.I64 }, new ValueType[]{});
        }
        @Override
        public USize[] call(USize... args) {
            if (args[0].uBidInteger().compareTo(args[1].uBidInteger()) != 0) {
                // 0 则为 true
                throw new RuntimeException("not equals: " + args[0] + " == " + args[1]);
            }
            return new USize[0];
        }
    }

    static class AssertEqualLong implements Function {
        @Override
        public FunctionType type() {
            return new FunctionType(FunctionTypeTag.FUNCTION_TYPE, new ValueType[]{ ValueType.I64, ValueType.I64 }, new ValueType[]{});
        }
        @Override
        public USize[] call(USize... args) {
            if (args[0].uBidInteger().compareTo(args[1].uBidInteger()) != 0) {
                // 0 则为 true
                throw new RuntimeException("not equals: " + args[0] + " == " + args[1]);
            }
            return new U64[0];
        }
    }

}
