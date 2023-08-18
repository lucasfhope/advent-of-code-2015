import java.io.File
import java.io.InputStream

fun main() {

    val inputStream: InputStream = File("input.txt").inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) } 
    
    var part1NiceCount = 0
    var part2NiceCount = 0
    
    lineList.forEach { str -> 
        if(isStringNicePart1(str)) part1NiceCount++
        if(isStringNicePart2(str)) part2NiceCount++
    }

    println("Part 1: There are $part1NiceCount nice strings.")
    println("Part 2: There are $part2NiceCount nice strings.")
}

/* returns true if the input string is considered
   nice based on the original rules decribed */
   
fun isStringNicePart1(input: String): Boolean {
	val vowels = listOf('a','e','i','o','u')
	val prohibitedDoubles = listOf("ab","cd","pq","xy")
	var numVowels = 0
    var doubleLetterFlag = false
    var previousChar: Char = Char(0)

    input.forEach { char ->
        if(char in vowels) {
            numVowels++
        }
        if(char == previousChar) {
            doubleLetterFlag = true
        }
        if(String(charArrayOf(previousChar,char)) in prohibitedDoubles) {
            return false
        }
        previousChar = char
    }

    if(numVowels >= 3 && doubleLetterFlag) {
        return true
    }
    return false
}

/* returns true if the input string is considered
   nice based on the rules decribed in part 2 */

fun isStringNicePart2(input: String): Boolean {
    var repeatDoubleFlag = false
    var splitLettersFlag = false
    var last3 = ""
	val previousDoubles = mutableListOf<String>()

    input.forEach { char ->
        if(last3.length == 3) {
            last3 = last3.substring(1,3)
        }
        last3 += char
        if(last3.length == 3) {
            if(last3.substring(1,3) in previousDoubles) {
                repeatDoubleFlag = true
            }
            if(last3.substring(0,1).equals(last3.substring(2,3))) {
                splitLettersFlag = true
            }
            previousDoubles.add(last3.substring(0,2))
        }
        if(repeatDoubleFlag && splitLettersFlag) {
            return true
        }
    } 
    return false
}
