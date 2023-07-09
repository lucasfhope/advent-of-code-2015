import java.io.File
import java.io.InputStream

fun main() {

    val inputStream: InputStream = File("input.txt").inputStream()
    
    val lightGrid = createLightGrid(1000,1000)

    inputStream.bufferedReader().forEachLine { cmd ->
        executeLightGridCommand(lightGrid, cmd)
    }

    val totalBrightness = countTotalBrightness(lightGrid)
    println("After following Santa's instructions, the total brightness of the lights was $totalBrightness.")   /* 17836115 */
}

fun executeLightGridCommand(lightGrid: Array<Array<Int>> , input: String) {
    
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

    val brightness = if(cmd_len == 4) 2 else if(input_split[1].equals("on")) 1 else -1

    changeLightGridBrightness(lightGrid, start_indices[0].toInt(), start_indices[1].toInt(), end_indices[0].toInt(), end_indices[1].toInt(), brightness)
}

fun createLightGrid(x: Int, y: Int): Array<Array<Int>> {
    return Array(x) { Array(y) { 0 } }
}

fun changeLightGridBrightness(lightGrid: Array<Array<Int>>, x_start: Int, y_start: Int, x_end: Int, y_end: Int, brightness: Int) {
    for(i in x_start .. x_end)
        for (j in y_start..y_end) {
            lightGrid[i][j] += brightness
            if(lightGrid[i][j] < 0)
                lightGrid[i][j] = 0
        }
}

fun countTotalBrightness(lightGrid: Array<Array<Int>>): Int {
    var count = 0
    for(light in lightGrid.flatten())
        count += light
    return count
}