import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {

    if(args.size != 1) {
        println("Error: Expected [house present total] as a command line argument.")
        return
    }

    val presentTarget = args[0].toInt()

    val duration1 = measureTimeMillis { findHouseWithPresentsPart1(presentTarget) }
    println("Time: ${duration1/1000.0} s")
    val duration2 = measureTimeMillis { findHouseWithPresentsPart2(presentTarget) }
    println("Time: ${duration2/1000.0} s")
}

fun findHouseWithPresentsPart1(presentTarget: Int) {
    var houseNumber = 1
    while(true) {
        val elvesThatVisit = findElvesThatVisitPart1(houseNumber)
        val presentsRecieved = elvesThatVisit.fold(0) { total, elfNumber -> total + elfNumber * 10 }
        if(presentsRecieved >= presentTarget) {
            println("Part 1: House number $houseNumber")
            return
        }
        houseNumber++
    }
}

fun findHouseWithPresentsPart2(presentTarget: Int) {
    var houseNumber = 1
    while (true) {
        val elvesThatVisit = findElvesThatVisitPart2(houseNumber)
        val presentsRecieved = elvesThatVisit.fold(0) { total, elfNumber -> total + elfNumber * 11 }
        if (presentsRecieved >= presentTarget) {
            println("Part 2: House number $houseNumber")
            return
        }
        houseNumber++
    }
}

fun findElvesThatVisitPart1(houseNumber: Int): List<Int> {
    val elfNumbers = mutableListOf<Int>()
    for (elfNumber in 1..houseNumber) {
        if (houseNumber % elfNumber == 0) {
            elfNumbers.add(elfNumber)
        }
    }
    return elfNumbers
}

fun findElvesThatVisitPart2(houseNumber: Int): List<Int> {
    val elfNumbers = mutableListOf<Int>()
    for (elfNumber in 1..houseNumber) {
        if (houseNumber % elfNumber == 0 && houseNumber / elfNumber <= 50) {
            elfNumbers.add(elfNumber)
        }
    }
    return elfNumbers
}
