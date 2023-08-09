import java.io.File
import java.io.InputStream

fun main() {

    val inputStream: InputStream = File("input.txt").inputStream()
    val instructions = inputStream.bufferedReader().use { it.readText() }
	
	visitHousesWithOnlySanta(instructions)
	visitHousesWithSantaAndRobot(instructions)
}

/* move santa with each instruction and
   count the number of new houses visited */

fun visitHousesWithOnlySanta(instructions: String) {
	var houses_with_presents = 1
	var coordinates = Pair(0,0)
	var coordinates_visited = mutableListOf(coordinates)

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

	println("$houses_with_presents houses recieved presents from Santa.")
}

/* move santa and robo-santa every other instruction and
  count the number of new houses visited */

fun visitHousesWithSantaAndRobot(instructions: String) {
	var houses_with_presents = 1
	var santa_coordinates = Pair(0,0)
	var robo_coordinates = Pair(0,0)
	var coordinates_visited = mutableListOf(Pair(0,0))
	var instructionNumber = 0
	var coordinates: Pair<Int,Int>

	instructions.forEach() {
		if(instructionNumber % 2 == 0) coordinates = santa_coordinates
		else coordinates = robo_coordinates

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

		if(instructionNumber % 2 == 0) santa_coordinates = coordinates
		else robo_coordinates = coordinates
		instructionNumber++
	}

	println("$houses_with_presents houses recieved presents from Santa and Robo-Santa.")
}
