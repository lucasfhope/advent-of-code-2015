import java.io.File
import java.io.InputStream

fun main() {

    val inputStream: InputStream = File("input.txt").inputStream()
    
    val lightGrid = createLightGrid(1000,1000)

    inputStream.bufferedReader().forEachLine { cmd ->
        executeLightGridCommand(lightGrid, cmd)
    }

    val lightCount = countIlluminatedLights(lightGrid)
    println("After following Santa's instructions, there were $lightCount lights left on.")   /* 569999 */
}


/* executeLightGridCommand() parses the cmd read 
   from input.txt so the lightGrid can be updated  */

fun executeLightGridCommand(lightGrid: Array<Array<Boolean>>, input: String) {
    
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

    if(cmd_len == 4) {
        toggleLightGrid(lightGrid, start_indices[0].toInt(), start_indices[1].toInt(), end_indices[0].toInt(), end_indices[1].toInt())
        return
    }

    val turnOn = input_split[1].equals("on")
    turnOnOffLightGrid(lightGrid, start_indices[0].toInt(), start_indices[1].toInt(), end_indices[0].toInt(), end_indices[1].toInt(), turnOn)
}


/* createLightGrid() returns a turned 
   off lightGrid of dimensions specified */ 

fun createLightGrid(x: Int, y: Int): Array<Array<Boolean>> {
    return Array(x) { Array(y) {false} }
}

/* turnOnOffLightGrid() turns a range 
   of lights in the lightGrid on or off */ 

fun turnOnOffLightGrid(lightGrid: Array<Array<Boolean>>, x_start: Int, y_start: Int, x_end: Int, y_end: Int, turnOn: Boolean) {
    for(i in x_start .. x_end)
        for(j in y_start .. y_end)
            lightGrid[i][j] = turnOn
}

/* toggleLightGrid() turns a range of lights in the 
   lightGrid the opposite of the current state */

fun toggleLightGrid(lightGrid: Array<Array<Boolean>> , x_start: Int, y_start: Int, x_end: Int, y_end: Int) {
    for(i in x_start .. x_end)
        for(j in y_start .. y_end)
            lightGrid[i][j] = !lightGrid[i][j]
}

/* countIlluminatedLights() returns the
   total number of lights that are on */

fun countIlluminatedLights(lightGrid: Array<Array<Boolean>>): Int {
    var count = 0
    for(light in lightGrid.flatten())
        if(light) count++
    return count
}
