
import java.io.File
import java.io.InputStream


fun main() {

    val mfcsamAuntSue = AuntSue(0,3,7,2,3,0,0,5,3,2,1)
    val auntSues = mutableListOf<AuntSue>()
    val outdatedSueComparisonValues = mutableMapOf<Int,Int>()
    val updatedSueComparisonValues = mutableMapOf<Int,Int>()

    var inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { mem ->
        auntSues.add(auntSueFromMemory(mem))
    }

    for(auntSue in auntSues) {
        outdatedSueComparisonValues[auntSue.sueNumber] = mfcsamAuntSue.compareOutdated(auntSue)
        updatedSueComparisonValues[auntSue.sueNumber] = mfcsamAuntSue.compareUpdated(auntSue)
    }

    val outdatedSue = outdatedSueComparisonValues.maxBy { it.value }
    val updatedSue = updatedSueComparisonValues.maxBy { it.value }

    println(outdatedSue.key)
    println(updatedSue.key)

}

fun auntSueFromMemory(input: String): AuntSue {
    val splitInput = input.split(" ")
    val sueNumber = splitInput[1].replace(":","").toInt()
    return AuntSue(
        sueNumber,
        getFieldValue(splitInput, "children"),
        getFieldValue(splitInput, "cats"),
        getFieldValue(splitInput, "samoyeds"),
        getFieldValue(splitInput, "pomeranians"),
        getFieldValue(splitInput, "akitas"),
        getFieldValue(splitInput, "vizslas"),
        getFieldValue(splitInput, "goldfish"),
        getFieldValue(splitInput, "trees"),
        getFieldValue(splitInput, "cars"),
        getFieldValue(splitInput, "perfumes")
    )
}

fun getFieldValue(inputList: List<String>, field: String): Int? {
    val fieldSearchKey = field + ":"
    for(i in 0 until inputList.size) {
        if(inputList[i].equals(fieldSearchKey)) {
            return inputList[i+1].replace(",","").toInt()
        }
    }
    return null
}


data class AuntSue(
    val sueNumber: Int,
    val children: Int?,
    val cats: Int?,
    val samoyeds: Int?,
    val pomeranians: Int?,
    val akitas: Int?,
    val vizslas: Int?,
    val goldfish: Int?,
    val trees: Int?,
    val cars: Int?,
    val perfumes: Int?
) {
    fun compareOutdated(sue: AuntSue): Int {
        var totalSame = 0
        if(this.children != null && sue.children != null && this.children == sue.children) totalSame++
        if(this.cats != null && sue.cats != null && this.cats == sue.cats) totalSame++
        if(this.samoyeds != null && sue.samoyeds != null && this.samoyeds == sue.samoyeds) totalSame++
        if(this.pomeranians != null && sue.pomeranians != null && this.pomeranians == sue.pomeranians) totalSame++
        if(this.akitas != null && sue.akitas != null && this.akitas == sue.akitas) totalSame++
        if(this.vizslas != null && sue.vizslas != null && this.vizslas == sue.vizslas) totalSame++
        if(this.goldfish != null && sue.goldfish != null && this.goldfish == sue.goldfish) totalSame++
        if(this.trees != null && sue.trees != null && this.trees == sue.trees) totalSame++
        if(this.cars != null && sue.cars != null && this.cars == sue.cars) totalSame++
        if(this.perfumes != null && sue.perfumes != null && this.perfumes == sue.perfumes) totalSame++
        return totalSame
    }
    fun compareUpdated(sue: AuntSue): Int {
        var total = 0
        if(this.children != null && sue.children != null && this.children == sue.children) total++
        if(this.cats != null && sue.cats != null && this.cats < sue.cats) total++
        if(this.samoyeds != null && sue.samoyeds != null && this.samoyeds == sue.samoyeds) total++
        if(this.pomeranians != null && sue.pomeranians != null && this.pomeranians > sue.pomeranians) total++
        if(this.akitas != null && sue.akitas != null && this.akitas == sue.akitas) total++
        if(this.vizslas != null && sue.vizslas != null && this.vizslas == sue.vizslas) total++
        if(this.goldfish != null && sue.goldfish != null && this.goldfish > sue.goldfish) total++
        if(this.trees != null && sue.trees != null && this.trees < sue.trees) total++
        if(this.cars != null && sue.cars != null && this.cars == sue.cars) total++
        if(this.perfumes != null && sue.perfumes != null && this.perfumes == sue.perfumes) total++
        return total
    }
}
