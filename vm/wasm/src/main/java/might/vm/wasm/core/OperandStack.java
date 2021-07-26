package might.vm.wasm.core;

import might.common.numeric.*;
import might.vm.wasm.error.module.OperandTypeException;
import might.vm.wasm.util.Slice;

public class OperandStack {

    private final Slice<ISize> slots = new Slice<>();

    public void pushISize(ISize value) { slots.append(value); }
    public ISize popISize() { return slots.remove(slots.size() - 1); }

    public void pushI8(I8 value) { pushISize(value); }
    public void pushI16(I16 value) { pushISize(value); }
    public void pushI32(I32 value) { pushISize(value); }
    public void pushI64(I64 value) { pushISize(value); }
    public void pushBool(boolean value) { pushISize(I32.valueOf(new byte[]{ 0,0,0, value ? (byte) 1 : 0 })); }
    public void pushS32(int value) { pushISize(I32.valueOf(value)); }
    public void pushS64(long value) { pushISize(I64.valueOf(value)); }

    public  I8  popI8() { ISize v = popISize(); if (v instanceof  I8) { return ( I8) v; } error(v,  I8.class); return null; }
    public I16 popI16() { ISize v = popISize(); if (v instanceof I16) { return (I16) v; } error(v, I16.class); return null; }
    public I32 popI32() { ISize v = popISize(); if (v instanceof I32) { return (I32) v; } error(v, I32.class); return null; }
    public I64 popI64() { ISize v = popISize(); if (v instanceof I64) { return (I64) v; } error(v, I64.class); return null; }
    public boolean popBool() { return popI32().booleanValue(); }
    public int popS32() { return popI32().signed().intValue(); }
    public long popS64() { return popI64().signed().longValue(); }

    private static void error(ISize v, Class<? extends ISize> c) throws RuntimeException {
        throw new OperandTypeException("not " + c.getName() + ": " + v + "(" + v.getClass().getName() + ")");
    }

    public int size() { return slots.size(); }

    public ISize  getOperandISize(int index) { return slots.get(index); }
    public  I8  getOperandI8(int index) { ISize v = slots.get(index); if (v instanceof  I8) { return ( I8) v; } error(v,  I8.class); return null; }
    public I16 getOperandI16(int index) { ISize v = slots.get(index); if (v instanceof I16) { return (I16) v; } error(v, I16.class); return null; }
    public I32 getOperandI32(int index) { ISize v = slots.get(index); if (v instanceof I32) { return (I32) v; } error(v, I32.class); return null; }
    public I64 getOperandI64(int index) { ISize v = slots.get(index); if (v instanceof I64) { return (I64) v; } error(v, I64.class); return null; }
    public boolean getOperandBoolean(int index) { ISize v = slots.get(index); if (v instanceof I8) { return v.booleanValue(); } error(v, I8.class); return false; }
    public int getOperandInt(int index) { ISize v = slots.get(index); if (v instanceof I32) { return v.signed().intValue(); } error(v, I32.class); return 0; }
    public long getOperandLong(int index) { ISize v = slots.get(index); if (v instanceof I64) { return v.signed().longValue(); } error(v, I64.class); return 0; }

    public void setOperand(int index,  ISize value)  { slots.set(index, value); }
    public void setOperand(int index,  I8 value)  { slots.set(index, value); }
    public void setOperand(int index, I16 value)  { slots.set(index, value); }
    public void setOperand(int index, I32 value)  { slots.set(index, value); }
    public void setOperand(int index, I64 value)  { slots.set(index, value); }
    public void setOperand(int index, boolean value)  { slots.set(index, I32.valueOf(new byte[]{ 0,0,0, value ? (byte) 1 : 0 })); }
    public void setOperand(int index, int value)  { slots.set(index, I32.valueOf(value)); }
    public void setOperand(int index, long value)  { slots.set(index, I64.valueOf(value)); }

    public void pushUSizes(ISize[] values) {
        for (ISize v : values) { pushISize(v); }
    }

    public ISize[] popUSizes(int size) {
        ISize[] values = new ISize[size];
        for (int i = size - 1; 0 <= i; i--) {
            values[i] = popISize();
        }
        return values;
    }

    public void clear() {
        slots.clear();
    }

}
