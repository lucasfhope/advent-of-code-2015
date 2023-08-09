
import java.io.File
import java.io.InputStream

fun main() {

    val inputStream: InputStream = File("input.txt").inputStream()
    val instructions = inputStream.bufferedReader().use { it.readText() }

    var floor = 0
    var instruction_number = 0
    var basement_enetered = false

    /* changes the floor number based on the instruction 
       and checks for the first time the basement is entered */

    for(instruction in instructions) {
        
        instruction_number++

        if(instruction == '(') {
            floor++
        } else if (instruction == ')') {
            floor--
        }

        if(floor == -1 && !basement_enetered) {
            println("Basement first enetered after instruction $instruction_number") 
            basement_enetered = true
        } 
    }

    println("Santa finished on floor $floor")
}
