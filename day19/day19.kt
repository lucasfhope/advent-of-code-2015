import java.io.File
import java.io.InputStream

fun main() {
    val moleculeReplacements = mutableMapOf<String,MutableList<String>>()
    val originalMolecule: String
    var molecule = ""

    var nextIsMolecule = false
    var inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { str ->
        if (nextIsMolecule) molecule = str
        else if (str.equals("")) nextIsMolecule = true
        else {
            val replacement = str.split(" => ")
            if(moleculeReplacements.containsKey(replacement[0]) && moleculeReplacements[replacement[0]] != null) {
                moleculeReplacements[replacement[0]]?.add(replacement[1])
            } else {
                moleculeReplacements[replacement[0]] = mutableListOf(replacement[1])
            }
        }
    }
    originalMolecule = molecule

    var replacementMolecules = generateReplacementMolecules(originalMolecule, moleculeReplacements)
    println("${replacementMolecules.size} total replacement molecules")
    val numSteps = backtrackToE(originalMolecule,moleculeReplacements)
    println("$numSteps steps from e to $originalMolecule")
}

fun generateReplacementMolecules(originalMolecule: String, moleculeReplacements: Map<String,List<String>>): Set<String> {
    val replacementMolecules = mutableSetOf<String>()
    for(i in 0 until originalMolecule.length) {
        val oneLetter = originalMolecule.substring(i,i+1)
        if(moleculeReplacements.containsKey(oneLetter)) {
            for(replacement in moleculeReplacements[oneLetter]!!) {
                replacementMolecules.add(originalMolecule.substring(0,i) +
                        replacement + originalMolecule.substring(i+1,originalMolecule.length))
            }
        }
        val twoLetter = if(i+2 <= originalMolecule.length) originalMolecule.substring(i,i+2) else null
        if(twoLetter != null && moleculeReplacements.containsKey(twoLetter)) {
            for(replacement in moleculeReplacements[twoLetter]!!) {
                replacementMolecules.add(originalMolecule.substring(0,i) +
                        replacement + originalMolecule.substring(i+2,originalMolecule.length))
            }
        }
    }
    return replacementMolecules
}

// returns the number of steps taken from e to the molecule
fun backtrackToE(medecineMolecule: String, moleculeReplacements: Map<String, List<String>>): Int {
    val listOfReplacements = listOfReplacements(moleculeReplacements)
    var currentMolecule = medecineMolecule
    var stepsTaken = 0

    while (!currentMolecule.equals("e")) {
        replacementLoop@ for(replacement in listOfReplacements) {
            for (i in 0..currentMolecule.length - replacement.length) {
                if (replacement.equals(currentMolecule.substring(i, i + replacement.length))) {
                    val moleculeReplaced = findKeyByValue(moleculeReplacements,replacement)
                    currentMolecule = currentMolecule.substring(0,i) + moleculeReplaced +
                            currentMolecule.substring(i+replacement.length,currentMolecule.length)
                    stepsTaken++
                    break@replacementLoop
                }
            }
        }
    }
    return stepsTaken
}

fun listOfReplacements(moleculeReplacements: Map<String, List<String>>): List<String> {
    val allValues = moleculeReplacements.values.flatten()
    val sortedByLength = allValues.sortedByDescending { it.length }
    return sortedByLength
}

fun findKeyByValue(moleculeReplacements: Map<String, List<String>>, molecule: String): String? {
    for ((key, value) in moleculeReplacements) {
        if (molecule in value) {
            return key
        }
    }
    return null
}
