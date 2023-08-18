import java.io.File
import java.io.InputStream


class Register(startingValue: Int) {
    var value = startingValue
}

fun main() {
    val inputStream: InputStream = File("input.txt").inputStream()
    val instructions = inputStream.bufferedReader().readLines()
    runProgram(instructions, Register(0), Register(0),Register(0))
    runProgram(instructions, Register(1), Register(0),Register(0))
}

fun runProgram(instructions: List<String>, A: Register, B: Register, PC: Register) {
    while (PC.value < instructions.size) {
        val splitInstruction = instructions[PC.value].split(" ")
        if (splitInstruction[0].equals("hlf")) {
            val register = if (splitInstruction[1].equals("a")) A else B
            register.value /= 2
            PC.value++
        } else if (splitInstruction[0].equals("tpl")) {
            val register = if (splitInstruction[1].equals("a")) A else B
            register.value *= 3
            PC.value++
        } else if (splitInstruction[0].equals("inc")) {
            val register = if (splitInstruction[1].equals("a")) A else B
            register.value++
            PC.value++
        } else if (splitInstruction[0].equals("jmp")) {
            val offset = splitInstruction[1].replace("+", "").toInt()
            PC.value += offset
        } else if (splitInstruction[0].equals("jie")) {
            val register = if (splitInstruction[1].replace(",", "").equals("a")) A else B
            val offset = splitInstruction[2].replace("+", "").toInt()
            if (register.value % 2 == 0) PC.value += offset
            else PC.value++
        } else if (splitInstruction[0].equals("jio")) {
            val register = if (splitInstruction[1].replace(",", "").equals("a")) A else B
            val offset = splitInstruction[2].replace("+", "").toInt()
            if (register.value == 1) PC.value += offset
            else PC.value++
        } else {
            System.err.println("Error: Unable to parse instruction.")
            PC.value++
        }
    }
    println("Register B: ${B.value}")
}


