import java.io.File
import java.io.InputStream

fun main() {

    var splitInput = listOf<String>()
    val inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { str ->
        splitInput = str.split(" ")
    }
    val codeRows = splitInput[16].replace(",", "").toInt()
    val codeColumns = splitInput[18].replace(".", "").toInt()


    var codeGridNumber = 1
    for(i in 1 .. codeRows) codeGridNumber += i-1
    for(i in 1 until codeColumns) codeGridNumber += codeRows+i

    var currentCode: Long = 20151125
    for(i in 2 .. codeGridNumber) {
        currentCode = (currentCode * 252533) % 33554393
    }
    println(currentCode)
}