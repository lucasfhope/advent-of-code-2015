import java.io.File
import java.io.InputStream

fun main() {
    val lightGridPart1 = createLightGrid(1000,1000)
	val lightGridPart2 = createLightGrid(1000,1000)

	val inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { cmd ->
        executeLightGridCommand(lightGridPart1, cmd, false)
		executeLightGridCommand(lightGridPart2, cmd, true)
    }

    val lightCountPart1 = countTotalBrightness(lightGridPart1)
	val lightCountPart2 = countTotalBrightness(lightGridPart2)

    println("$lightCountPart1 lights left on.")
	println("$lightCountPart2 lights left on.")
}


fun executeLightGridCommand(lightGrid: Array<Array<Int>>, input: String, isPart2: Boolean) {
    val input_split = input.split(" ")
    val cmd_len = input_split.size
    
    if(cmd_len != 4 && cmd_len != 5) {
        System.err.println("Command Execution Error: Unable to parse command.")
        return
    }

    val start_indices = input_split[cmd_len-3].split(",")
    val end_indices = input_split[cmd_len-1].split(",")
    
    if(start_indices.size != 2 && end_indices.size != 2) {
        System.err.println("Command Execution Error: Unable to parse indeces.")
        return
    }

	val toggle = cmd_len == 4
	val brightness = if(toggle) 2 else if(input_split[1].equals("on")) 1 else if(!isPart2) 0 else -1

	changeLightGridBrightness(lightGrid, start_indices[0].toInt(), start_indices[1].toInt(), end_indices[0].toInt(), end_indices[1].toInt(), brightness, toggle, isPart2)
}


fun changeLightGridBrightness(lightGrid: Array<Array<Int>>, x_start: Int, y_start: Int, x_end: Int, y_end: Int, brightness: Int, toggle: Boolean, isPart2: Boolean) {
	for(i in x_start .. x_end) {
		for (j in y_start .. y_end) {
			if(!isPart2) {
				if(toggle) {
					lightGrid[i][j] = if(lightGrid[i][j] == 0) 1 else 0
				} else {
					lightGrid[i][j] = brightness
				}
			} else {
				lightGrid[i][j] += brightness
				if(lightGrid[i][j] < 0) lightGrid[i][j] = 0
			}
		}
	}
}

fun createLightGrid(x: Int, y: Int): Array<Array<Int>> {
	return Array(x) { Array(y) { 0 } }
}

fun countTotalBrightness(lightGrid: Array<Array<Int>>): Int {
	var count = 0
	for(light in lightGrid.flatten()) count += light
	return count
}



