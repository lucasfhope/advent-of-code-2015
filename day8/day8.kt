import java.io.File
import java.io.InputStream
import java.util.regex.Pattern

val quote_pattern = Regex("\\\\\\\"")
val backslash_pattern = Regex("\\\\\\\\")
val hexadecimal_pattern = Regex("\\\\x[0-9A-Fa-f]{2}")

fun main() {
    var totalCharacters = 0
    var inMemoryCharacters = 0
    var newEncodingCharacters = 0

    val inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { str ->
        totalCharacters += countTotalCharacters(str)
        inMemoryCharacters += countInMemoryCharacters(str)
        newEncodingCharacters += countNewEncodingCharacters(str)
    }

    val difference_pt1 = totalCharacters - inMemoryCharacters
    println("The difference between the number of characters total and in memory is $difference_pt1.")
    val difference_pt2 = newEncodingCharacters - totalCharacters
    println("The difference between the number of newly encoded and total characters is $difference_pt2.")
}

fun countTotalCharacters(input: String): Int = input.length

fun countInMemoryCharacters(input: String): Int {
    // remove outside quotes
    var replaced_input = input.substring(1, input.length-1)
    // replace escape sequence
    replaced_input = quote_pattern.replace(replaced_input, "_")
    // replace escaped quotes
    replaced_input = backslash_pattern.replace(replaced_input, "_")
    // replace hexadecimal characters
    replaced_input = hexadecimal_pattern.replace(replaced_input, "_")
    return countTotalCharacters(replaced_input)
}

fun countNewEncodingCharacters(input: String): Int {
    // add placeholders for encoded outside quotes
    var replaced_input = "___" + input.substring(1, input.length-1) + "___"
    // add placeholders for encoded escape quotes
    replaced_input = quote_pattern.replace(replaced_input, "____")
    // add placeholders for encoded escape sequence
    replaced_input = backslash_pattern.replace(replaced_input, "____")
    // add placeholders for encoded hexadecimal
    replaced_input = hexadecimal_pattern.replace(replaced_input, "__x__")
    return countTotalCharacters(replaced_input)
}

