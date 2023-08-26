import java.math.BigInteger
import java.security.MessageDigest

fun main(args: Array<String>) {

    if (args.size != 2) {
        System.err.println("Usage: [puzzle input] and [number of iterations] expected as command line arguments.")
        return
    }

    var sequence: String = args[0]
    var iterations: Int = args[1].toInt()
    while(iterations > 0) {
        sequence = lookAndSay(sequence)
        iterations--
    }

    println("The look and say puzzle result length is ${sequence.length}.")
}

fun lookAndSay(input: String): String {
    var oldSequence = input
    var newSequence = ""
    while(oldSequence.length > 0) {
        val count = countRepeatingNumbers(oldSequence, oldSequence[0])
        newSequence += (count.toString() + oldSequence[0])
        oldSequence = oldSequence.substring(count)
    }
    return newSequence
}

fun countRepeatingNumbers(input: String, repeatingNumber: Char): Int {
    var sequence = input
    var count = 0
    while(sequence.length > 0 && sequence[0] == repeatingNumber) {
        count++
        sequence = sequence.substring(1)
    }
    return count
}
