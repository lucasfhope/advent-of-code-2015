import java.io.File
import java.io.InputStream


val people = mutableListOf<Person>()

fun main() {

    var inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { str ->
        createPerson(str)
    }

    val arrangements = generateSeatingArrangements(people)
    var bestHappinessValue = Int.MIN_VALUE
    for(arrangement in arrangements) {
        val arrangementHappinessValue = calculateTotalHappiness(arrangement)
        if(arrangementHappinessValue > bestHappinessValue) bestHappinessValue = arrangementHappinessValue
    }

    println("$bestHappinessValue total happiness without me")

    addMyself()

    val newArrangements = generateSeatingArrangements(people)
    var newBestHappinessValue = Int.MIN_VALUE
    for(arrangement in newArrangements) {
        val arrangementHappinessValue = calculateTotalHappiness(arrangement)
        if(arrangementHappinessValue > newBestHappinessValue) newBestHappinessValue = arrangementHappinessValue
    }

    println("newBestHappinessValue total happiness with me")
}

fun generateSeatingArrangements(inputPeople: List<Person>): List<List<Person>> {
    if(inputPeople.size <= 1) return listOf(inputPeople)
    val seatingArrangements = mutableListOf<List<Person>>()
    for(i in inputPeople.indices) {
        val person = inputPeople[i]
        val remainingPeople = inputPeople.minus(person)
        val subArrangements = generateSeatingArrangements(remainingPeople)
        for(subArrangement in subArrangements) {
            val arrangement = mutableListOf(person)
            arrangement.addAll(subArrangement)
            seatingArrangements.add(arrangement)
        }
    }
    return seatingArrangements
}

fun calculateTotalHappiness(arrangement: List<Person>): Int {
    var totalHappiness = 0
    for(i in 0 until arrangement.size) {
        val leftNeighbor: String
        val rightNeighbor: String
        if(i == 0) leftNeighbor = arrangement[arrangement.size-1].name
        else leftNeighbor = arrangement[i-1].name
        if(i == arrangement.size-1) rightNeighbor = arrangement[0].name
        else rightNeighbor = arrangement[i+1].name
        var leftHappiness: Int? = arrangement[i].happinessPreferences[leftNeighbor]
        if(leftHappiness == null) leftHappiness = 0
        var rightHappiness: Int? = arrangement[i].happinessPreferences[rightNeighbor]
        if(rightHappiness == null) rightHappiness = 0
        totalHappiness += leftHappiness + rightHappiness
    }
    return totalHappiness
}

fun createPerson(input: String) {
    val splitInput = input.split(" ")
    var person = people.find { it.name == splitInput[0] }
    if(person == null) {
        person = Person(splitInput[0])
        people.add(person)
    }
    val lose = splitInput[2].equals("lose")
    var happinessValue = splitInput[3].toInt()
    if(lose) happinessValue *= -1
    val otherPerson = splitInput[10].replace(".","")
    person.happinessPreferences.put(otherPerson,happinessValue)
}

fun addMyself() {
    val me = Person("Lucas")
    for(person in people) {
        me.happinessPreferences.put(person.name,0)
    }
    people.add(me)
}

class Person(personName: String) {
    val name = personName
    val happinessPreferences = mutableMapOf<String,Int>()
}
