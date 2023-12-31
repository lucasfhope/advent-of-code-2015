### --- Day 23: Opening the Turing Lock ---

Little Jane Marie just got her very first computer for Christmas from some unknown benefactor. It comes with instructions and an example program, but the computer itself seems to be malfunctioning. She's curious what the program does, and would like you to help her run it.

The manual explains that the computer supports two registers and six instructions (truly, it goes on to remind the reader, a state-of-the-art technology). The registers are named `a` and `b`, can hold any non-negative integer, and begin with a value of `0`. The instructions are as follows:

- `hlf r` sets register `r` to __half__ its current value, then continues with the next instruction.
- `tpl r` sets register `r` to __triple__ its current value, then continues with the next instruction.
- `inc r` __increments__ register `r`, adding `1` to it, then continues with the next instruction.
- `jmp offset` is a __jump__; it continues with the instruction `offset` away __relative to itself__.
- `jie r, offset` is like `jmp`, but only jumps if register `r` is __even__ ("jump if even").
- `jio r, offset` is like `jmp`, but only jumps if register `r` is `1` ("jump if __one__", not odd).

All three jump instructions work with an __offset__ relative to that instruction. The offset is always written with a prefix `+` or `-` to indicate the direction of the jump (forward or backward, respectively). For example, `jmp +1` would simply continue with the next instruction, while `jmp +0` would continuously jump back to itself forever.

The program exits when it tries to run an instruction beyond the ones defined.

For example, this program sets `a` to `2`, because the `jio` instruction causes it to skip the `tpl` instruction:

    inc a
    jio a, +2
    tpl a
    inc a

What is the __value in register__ `b` when the program in your puzzle input is finished executing?

Your puzzle answer was `184`.

### --- Part Two ---

The unknown benefactor is __very__ thankful for releasi-- er, helping little Jane Marie with her computer. Definitely not to distract you, what is the value in register `b` after the program is finished executing if register `a` starts as `1` instead?

Your puzzle answer was `231`.

___

You can [get the puzzle input here](input.txt).
