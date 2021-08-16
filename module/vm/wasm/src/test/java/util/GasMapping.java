package util;

import expanse.vm.wasm.instruction.Instruction;

import java.util.HashMap;
import java.util.Map;

public class GasMapping {

    public static final Map<Instruction, Long> TEST = new HashMap<>();

    static {
        Map<Instruction, Long> map = new HashMap<>();
        for (Instruction i : Instruction.values()) { map.put(i, 0xFFFFFFFFL); }

        map.put(UNREACHABLE,   10L);
        map.put(NOP,           10L);
        map.put(BLOCK,         10L);
        map.put(LOOP,          10L);
        map.put(IF_BLOCK,      10L);

        map.put(BR,            10L);
        map.put(BR_IF,         10L);
        map.put(BR_TABLE,      10L);
        map.put(RETURN,        10L);
        map.put(CALL,          10L);
        map.put(CALL_INDIRECT, 10L);

        // 引用指令
        map.put(REF_NULL,       1L);
        map.put(REF_IS_NULL,    1L);
        map.put(REF_FUNC,       1L);

        // 参数指令
        map.put(DROP,           1L);
        map.put(SELECT,         2L);
        map.put(SELECT_C,       2L);

        // 变量指令
        map.put(LOCAL_GET,  1L);
        map.put(LOCAL_SET,  1L);
        map.put(LOCAL_TEE,  2L);
        map.put(GLOBAL_GET, 4L);
        map.put(GLOBAL_SET, 4L);

        // 表指令
        map.put(TABLE_GET,      4L);
        map.put(TABLE_SET,      4L);
        map.put(TABLE_INIT,    10L);
        map.put(TABLE_DROP,    10L);
        map.put(TABLE_COPY,     6L);
        map.put(TABLE_GROW,     2L);
        map.put(TABLE_SIZE,     1L);
        map.put(TABLE_FILL,     4L);

        // 内存指令
        map.put(I32_LOAD, 4L);
        map.put(I64_LOAD, 4L);
        // 浮点数操作 0x2A m:memarg => f32.load m
        // 浮点数操作 0x2B m:memarg => f64.load m
        map.put(I32_LOAD8_S,    4L);
        map.put(I32_LOAD8_U,    4L);
        map.put(I32_LOAD16_S,   4L);
        map.put(I32_LOAD16_U,   4L);
        map.put(I64_LOAD8_S,    4L);
        map.put(I64_LOAD8_U,    4L);
        map.put(I64_LOAD16_S,   4L);
        map.put(I64_LOAD16_U,   4L);
        map.put(I64_LOAD32_S,   4L);
        map.put(I64_LOAD32_U,   4L);
        map.put(I32_STORE,      4L);
        map.put(I64_STORE,      4L);
        // 浮点数操作 0x38 m:memarg => f32.store m
        // 浮点数操作 0x39 m:memarg => f64.store m
        map.put(I32_STORE8,     4L);
        map.put(I32_STORE16,    4L);
        map.put(I64_STORE8,     4L);
        map.put(I64_STORE16,    4L);
        map.put(I64_STORE32,    4L);
        map.put(MEMORY_SIZE,    1L);
        map.put(MEMORY_GROW,    2L);
        map.put(MEMORY_INIT,   10L);
        map.put(DATA_DROP,     10L);
        map.put(MEMORY_COPY,    6L);
        map.put(MEMORY_FILL,    4L);

        // 数值指令
        map.put(I32_CONST, 1L);
        map.put(I64_CONST, 1L);
        // 浮点数操作 0x43 z:f32 => f32.const z
        // 浮点数操作 0x44 z:f64 => f64.const z
        map.put(I32_EQZ,    1L);
        map.put(I32_EQ,     2L);
        map.put(I32_NE,     2L);
        map.put(I32_LT_S,   2L);
        map.put(I32_LT_U,   2L);
        map.put(I32_GT_S,   2L);
        map.put(I32_GT_U,   2L);
        map.put(I32_LE_S,   2L);
        map.put(I32_LE_U,   2L);
        map.put(I32_GE_S,   2L);
        map.put(I32_GE_U,   2L);
        map.put(I64_EQZ,    2L);
        map.put(I64_EQ,     2L);
        map.put(I64_NE,     2L);
        map.put(I64_LT_S,   2L);
        map.put(I64_LT_U,   2L);
        map.put(I64_GT_S,   2L);
        map.put(I64_GT_U,   2L);
        map.put(I64_LE_S,   2L);
        map.put(I64_LE_U,   2L);
        map.put(I64_GE_S,   2L);
        map.put(I64_GE_U,   2L);
        // 浮点数操作 0x5B => f32.eq
        // 浮点数操作 0x5C => f32.ne
        // 浮点数操作 0x5D => f32.lt
        // 浮点数操作 0x5E => f32.gt
        // 浮点数操作 0x5F => f32.le
        // 浮点数操作 0x60 => f32.ge
        // 浮点数操作 0x61 => f64.eq
        // 浮点数操作 0x62 => f64.ne
        // 浮点数操作 0x63 => f64.lt
        // 浮点数操作 0x64 => f64.gt
        // 浮点数操作 0x65 => f64.le
        // 浮点数操作 0x66 => f64.ge
        map.put(I32_CLZ,    1L);
        map.put(I32_CTZ,    1L);
        map.put(I32_POPCNT, 1L);
        map.put(I32_ADD,    2L);
        map.put(I32_SUB,    2L);
        map.put(I32_MUL,    2L);
        map.put(I32_DIV_S,  2L);
        map.put(I32_DIV_U,  2L);
        map.put(I32_REM_S,  2L);
        map.put(I32_REM_U,  2L);
        map.put(I32_AND,    2L);
        map.put(I32_OR,     2L);
        map.put(I32_XOR,    2L);
        map.put(I32_SHL,    1L);
        map.put(I32_SHR_S,  1L);
        map.put(I32_SHR_U,  1L);
        map.put(I32_ROTL,   1L);
        map.put(I32_ROTR,   1L);
        map.put(I64_CLZ,    1L);
        map.put(I64_CTZ,    1L);
        map.put(I64_POPCNT, 1L);
        map.put(I64_ADD,    2L);
        map.put(I64_SUB,    2L);
        map.put(I64_MUL,    2L);
        map.put(I64_DIV_S,  2L);
        map.put(I64_DIV_U,  2L);
        map.put(I64_REM_S,  2L);
        map.put(I64_REM_U,  2L);
        map.put(I64_AND,    2L);
        map.put(I64_OR,     2L);
        map.put(I64_XOR,    2L);
        map.put(I64_SHL,    1L);
        map.put(I64_SHR_S,  1L);
        map.put(I64_SHR_U,  1L);
        map.put(I64_ROTL,   1L);
        map.put(I64_ROTR,   1L);
        // 浮点数操作 0x8B => f32.abs
        // 浮点数操作 0x8C => f32.neg
        // 浮点数操作 0x8D => f32.ceil
        // 浮点数操作 0x8E => f32.floor
        // 浮点数操作 0x8F => f32.trunc
        // 浮点数操作 0x90 => f32.nearest
        // 浮点数操作 0x91 => f32.sqrt
        // 浮点数操作 0x92 => f32.add
        // 浮点数操作 0x93 => f32.sub
        // 浮点数操作 0x94 => f32.mul
        // 浮点数操作 0x95 => f32.div
        // 浮点数操作 0x96 => f32.min
        // 浮点数操作 0x97 => f32.max
        // 浮点数操作 0x98 => f32.copysign
        // 浮点数操作 0x99 => f64.abs
        // 浮点数操作 0x9A => f64.neg
        // 浮点数操作 0x9B => f64.ceil
        // 浮点数操作 0x9C => f64.floor
        // 浮点数操作 0x9D => f64.trunc
        // 浮点数操作 0x9E => f64.nearest
        // 浮点数操作 0x9F => f64.sqrt
        // 浮点数操作 0xA0 => f64.add
        // 浮点数操作 0xA1 => f64.sub
        // 浮点数操作 0xA2 => f64.mul
        // 浮点数操作 0xA3 => f64.div
        // 浮点数操作 0xA4 => f64.min
        // 浮点数操作 0xA5 => f64.max
        // 浮点数操作 0xA6 => f64.copysign
        map.put(I32_WRAP_I64, 1L);
        // 浮点数操作 0xA8 => i32.trunc_f32_s
        // 浮点数操作 0xA9 => i32.trunc_f32_u
        // 浮点数操作 0xAA => i32.trunc_f64_s
        // 浮点数操作 0xAB => i32.trunc_f64_u
        map.put(I64_EXTEND_I32_S, 1L);
        map.put(I64_EXTEND_I32_U, 1L);
        // 浮点数操作 0xAE => i64.trunc_f32_s
        // 浮点数操作 0xAF => i64.trunc_f32_u
        // 浮点数操作 0xB0 => i64.trunc_f64_s
        // 浮点数操作 0xB1 => i64.trunc_f64_u
        // 浮点数操作 0xB2 => f32.convert_i32_s
        // 浮点数操作 0xB3 => f32.convert_i32_u
        // 浮点数操作 0xB4 => f32.convert_i64_s
        // 浮点数操作 0xB5 => f32.convert_i64_u
        // 浮点数操作 0xB6 => f32.demote_f64
        // 浮点数操作 0xB7 => f64.convert_i32_s
        // 浮点数操作 0xB8 => f64.convert_i32_u
        // 浮点数操作 0xB9 => f64.convert_i64_s
        // 浮点数操作 0xBA => f64.convert_i64_u
        // 浮点数操作 0xBB => f64.promote_f32
        // 浮点数操作 0xBC => i32.reinterpret_f32
        // 浮点数操作 0xBD => i64.reinterpret_f64
        // 浮点数操作 0xBE => f32.reinterpret_i32
        // 浮点数操作 0xBF => f64.reinterpret_f64
        map.put(I32_EXTEND8_S , 1L);
        map.put(I32_EXTEND16_S, 1L);
        map.put(I64_EXTEND8_S , 1L);
        map.put(I64_EXTEND16_S, 1L);
        map.put(I64_EXTEND32_S, 1L);

        map.forEach(TEST::put);
    }

}
