import java.io.File
import java.io.InputStream

fun main() {

    var lightGrid = createLightGrid(100, 100)
    var cornersOnLightGrid = createLightGrid(100,100)

    var row = 0
    var inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { str ->
        lightGrid = fillLightGridRow(lightGrid, str, row)
        cornersOnLightGrid = fillLightGridRow(cornersOnLightGrid, str, row++)
    }

    cornersOnLightGrid[0][0] = '#'
    cornersOnLightGrid[0][99] = '#'
    cornersOnLightGrid[99][0] = '#'
    cornersOnLightGrid[99][99] = '#'

    for(i in 1..100) {
        lightGrid = updateLightGridStatus(lightGrid)
        cornersOnLightGrid = updateLightGridStatusCornersOn(cornersOnLightGrid)
    }

    println("${numberOfLightsOn(lightGrid)} lights left on")
    println("${numberOfLightsOn(cornersOnLightGrid)} lights left on when corner lights stay on")
}

fun updateLightGridStatus(lightGrid: Array<Array<Char>>): Array<Array<Char>> {
    val newLightGrid = createLightGrid(100,100)
    for(i in 0 until lightGrid.size) {
        for(j in 0 until lightGrid[0].size) {
            newLightGrid[i][j] = updateLightStatus(lightGrid,i,j)
        }
    }
    return newLightGrid
}

fun updateLightGridStatusCornersOn(lightGrid: Array<Array<Char>>): Array<Array<Char>> {
    val newLightGrid = createLightGrid(100,100)
    for(i in 0 until lightGrid.size) {
        for(j in 0 until lightGrid[0].size) {
            if((i == 0 && j == 0) || (i == 0 && j == 99) || (i == 99 && j == 0) || (i == 99 && j == 99)) {
                newLightGrid[i][j] = '#'
            } else {
                newLightGrid[i][j] = updateLightStatus(lightGrid, i, j)
            }
        }
    }
    return newLightGrid
}

fun updateLightStatus(lightGrid: Array<Array<Char>>, x: Int, y: Int): Char {
    val adjacentLightsOn = calculateAdjacentLightsTurnedOn(lightGrid,x,y)
    if(lightGrid[x][y] == '#') {
        return when(adjacentLightsOn) {
            2,3 -> '#'
            else -> '.'
        }
    } else {
        return when(adjacentLightsOn) {
            3 -> '#'
            else -> '.'
        }
    }
}

fun calculateAdjacentLightsTurnedOn(lightGrid: Array<Array<Char>>, x: Int, y: Int): Int {
    return checkAdjacentLight(lightGrid,x-1,y-1) + checkAdjacentLight(lightGrid,x,y-1) +
            checkAdjacentLight(lightGrid,x+1,y-1) + checkAdjacentLight(lightGrid,x-1,y) +
                checkAdjacentLight(lightGrid,x+1,y) + checkAdjacentLight(lightGrid,x-1,y+1) +
                    checkAdjacentLight(lightGrid,x,y+1) + checkAdjacentLight(lightGrid,x+1,y+1)
}

fun checkAdjacentLight(lightGrid: Array<Array<Char>>, x: Int, y: Int): Int {
    if(x < 0 || x >= 100 || y < 0 || y >= 100) return 0
    return when(lightGrid[x][y]) {
        '#' -> 1
        else -> 0
    }
}

fun createLightGrid(x: Int, y: Int): Array<Array<Char>> {
    return Array(x) { Array(y) {'.'} }
}

fun fillLightGridRow(lightGrid: Array<Array<Char>>, input: String, row: Int): Array<Array<Char>> {
    for(i in 0 until 100) {
        lightGrid[row][i] = input[i]
    }
    return lightGrid
}

fun numberOfLightsOn(lightGrid: Array<Array<Char>>): Int {
    var lightsOn = 0
    for(i in 0 until lightGrid.size) {
        for(j in 0 until lightGrid[0].size) {
            if(lightGrid[i][j] == '#') lightsOn++
        }
    }
    return lightsOn
}

// for testing purposes
fun printLightGrid(lightGrid: Array<Array<Char>>) {
    for(i in 0 until lightGrid.size) {
        for(j in 0 until lightGrid[0].size) {
            print(lightGrid[i][j])
        }
        println()
    }
}
