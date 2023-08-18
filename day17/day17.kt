import java.io.File
import java.io.InputStream

fun main() {

    val containers = mutableListOf<Int>()

    var inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { str ->
        containers.add(str.toInt())
    }

    val containerCombinations = combinations(containers, 150)

    println(containerCombinations.size)

    val combinationWithMinimumContainers = containerCombinations.minByOrNull { it.size }
    val minimumContainersUsed = combinationWithMinimumContainers?.size

    var numCombinationsWithMinimumContainers = 0
    for(combination in containerCombinations) {
        if(combination.size == minimumContainersUsed) {
            numCombinationsWithMinimumContainers++
        }
    }

    println(numCombinationsWithMinimumContainers)

}

fun combinations(containers: MutableList<Int>, target: Int): List<List<Int>> {
    val result = mutableListOf<List<Int>>()

    fun backtrack(start: Int, target: Int, combination: MutableList<Int>) {
        if(target == 0) {
            result.add(combination.toList())
            return
        }
        for(i in start until containers.size) {
            if(containers[i] <= target) {
                combination.add(containers[i])
                backtrack(i+1, target - containers[i], combination)
                combination.removeAt(combination.size-1)
            }
        }
    }

    containers.sort()
    backtrack(0,target, mutableListOf<Int>())
    return result
}