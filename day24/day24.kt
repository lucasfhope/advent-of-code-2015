import java.io.File
import java.io.InputStream


fun main() {
    val inputStream: InputStream = File("input.txt").inputStream()
    val packages = mutableListOf<Int>()
    inputStream.bufferedReader().forEachLine {
        packages.add(it.toInt())
    }

    val groupSize3 = packages.sum() / 3
    val possibleCombinationsForFrontSeat3 = getPackageCombinations(packages,groupSize3)
    val mostLegRoomPackageCombinations3 = getPackagesCombinationsWithMostLegRoom(possibleCombinationsForFrontSeat3)
    findBestQuantumEntanglement(mostLegRoomPackageCombinations3)

    val groupSize4 = packages.sum() / 4
    val possibleCombinationsForFrontSeat4 = getPackageCombinations(packages,groupSize4)
    val mostLegRoomPackageCombinations4 = getPackagesCombinationsWithMostLegRoom(possibleCombinationsForFrontSeat4)
    findBestQuantumEntanglement(mostLegRoomPackageCombinations4)
}

fun getPackageCombinations(packages: List<Int>, targetSize: Int): List<List<Int>> {
    val packageCombinations = mutableListOf<List<Int>>()

    fun generatePackageCombination(currentIndex: Int, currentCombination: List<Int>) {
        if (currentIndex == packages.size) {
            if (currentCombination.sum() == targetSize) {
                packageCombinations.add(currentCombination)
            }
            return
        }

        val currentPackage = packages[currentIndex]
        val nextIndex = currentIndex + 1

        // don't add package to combination
        generatePackageCombination(nextIndex, currentCombination)
        // add package to combination if below the target size
        if(currentCombination.sum() + currentPackage <= targetSize) {
            generatePackageCombination(nextIndex, currentCombination + currentPackage)
        }
    }

    generatePackageCombination(0, emptyList())
    return packageCombinations
}

fun getPackagesCombinationsWithMostLegRoom(packageCombinations: List<List<Int>>): List<List<Int>> {
    val mostLegRoomCombinations = mutableListOf<List<Int>>()
    val minimumCombinationSize = packageCombinations.minByOrNull { it.size }?.size ?: 0
    for(packageCombination in packageCombinations) {
        if(packageCombination.size == minimumCombinationSize) {
            mostLegRoomCombinations.add(packageCombination)
        }
    }
    return mostLegRoomCombinations
}

fun findBestQuantumEntanglement(packageCombinations: List<List<Int>>) {
    var bestQuantumEntanglement = Long.MAX_VALUE
    var packageCombinationWithBestQuantumEntanglement = listOf<Int>()
    for(packageCombination in packageCombinations) {
        var quantumEntanglement: Long = 1
        for(pckge in packageCombination) {
            quantumEntanglement *= pckge.toLong()
        }
        if(quantumEntanglement < bestQuantumEntanglement) {
            bestQuantumEntanglement = quantumEntanglement
            packageCombinationWithBestQuantumEntanglement = packageCombination
        }
    }
    println(packageCombinationWithBestQuantumEntanglement)
    println(bestQuantumEntanglement)
}
