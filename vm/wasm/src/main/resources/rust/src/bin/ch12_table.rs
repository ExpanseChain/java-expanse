#![no_std]
#![no_main]

#[panic_handler]
fn panic(_: &core::panic::PanicInfo) -> ! {
    loop {}
}

type Binop = fn(i32, i32) -> i32;
fn add(a: i32, b: i32) -> i32 { a + b }
fn sub(a: i32, b: i32) -> i32 { a - b }
fn mul(a: i32, b: i32) -> i32 { a * b }
fn div(a: i32, b: i32) -> i32 { a / b }

#[no_mangle]
pub extern "C" fn calc(op: usize, a: i32, b: i32) -> i32 {
    get_fn(op)(a, b)
}

fn get_fn(op: usize) -> Binop {
    match op {
        1 => add,
        2 => sub,
        3 => mul,
        _ => div,
    }
}
