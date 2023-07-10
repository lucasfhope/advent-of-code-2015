import java.io.File
import java.io.InputStream

fun main() {

    val inputStream: InputStream = File("input.txt").inputStream()
    val instructions = inputStream.bufferedReader().use { it.readText() }

    var houses_with_presents = 1
    var coordinates = Pair(0,0) 

    var coordinates_visited = mutableListOf(coordinates)

    /* move santa with each instruction and 
       count the number of new houses visited */
    
    instructions.forEach() {
       
        when(it) {
            '^' -> coordinates = coordinates.copy(second = coordinates.second + 1)
            'v' -> coordinates = coordinates.copy(second = coordinates.second - 1)
            '>' -> coordinates = coordinates.copy(first = coordinates.first + 1)
            '<' -> coordinates = coordinates.copy(first = coordinates.first - 1)
        }

        if(coordinates !in coordinates_visited) {
            houses_with_presents++
            coordinates_visited.add(coordinates)
        }
    }

    println("$houses_with_presents houses recieved presents from Santa.")   /* 2572 */
}
