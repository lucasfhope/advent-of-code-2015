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

    println("After following Santa's instructions, there were $lightCountPart1 lights left on.")
	println("After following Santa's instructions, there were $lightCountPart2 lights left on.")
}

/* executeLightGridCommand() parses the cmd read 
   from input.txt so the lightGrid can be updated  */

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

/* createLightGrid() returns a turned 
   off lightGrid of dimensions specified */

fun createLightGrid(x: Int, y: Int): Array<Array<Int>> {
	return Array(x) { Array(y) { 0 } }
}

/* countTotalBrightness() returns the
   total brightness of the lightGrid in part 2
   or the number of illuminated lights in part 1 */

fun countTotalBrightness(lightGrid: Array<Array<Int>>): Int {
	var count = 0
	for(light in lightGrid.flatten()) count += light
	return count
}

/* changeLightGridBrightness() updates a range of lights in the lightGrid by a
   brightness of -1, 1, or 2 for part 2 and will turn light on (1) or off (0)
   or toggle whether the light is on or off for part 1 */

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

