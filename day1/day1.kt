
import java.io.File
import java.io.InputStream

fun main() {
    val inputStream: InputStream = File("input.txt").inputStream()
    val instructions = inputStream.bufferedReader().use { it.readText() }

    var floor = 0
    var instruction_number = 0
    var basement_enetered = false

    for(instruction in instructions) {
        instruction_number++
        if(instruction == '(') floor++
		else if (instruction == ')') floor--
        if(floor == -1 && !basement_enetered) {
            println("Basement entered after instruction $instruction_number")
            basement_enetered = true
        } 
    }
    println("Instructions lead to floor $floor")
}
