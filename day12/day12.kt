
import java.io.File
import java.nio.charset.Charset

var discludeRed = false

fun main() {
    val inputJson: String = File("input.txt").inputStream().readBytes().toString(Charsets.UTF_8)

    val sumOfNumbers = findJsonObjectValue(inputJson, inputJson.length)
    discludeRed = true
    val sumOfNumbersDiscludingRed = findJsonObjectValue(inputJson, inputJson.length)

    println("The sum of numbers is $sumOfNumbers.")
    println("The sum of numbers discluding red is $sumOfNumbersDiscludingRed.")
}

/* value */

fun findJsonObjectValue(input: String, length: Int): Int {
    var count = 0
    var i = 0
    while(i < length) {  		// length includes }
        if(input[i] == '{') {
            val len = findJsonObjectLength(input.substring(i + 1))
            count += findJsonObjectValue(input.substring(i + 1),len)
            i += len
        } else if(input[i] == '[') {
            val len = findJsonArrayLength(input.substring(i+1))
            count += findJsonArrayValue(input.substring(i+1),len)
            i += len
        } else if(input[i] == '"') {
            val len = findJsonStrLength(input.substring(i+1))
            if(findJsonStrValue(input.substring(i+1),len).equals("red") && discludeRed) {
                return 0
            }
            i += len
        } else if(input[i] in '0' .. '9' || input[i] == '-') {
            val len = findJsonNumberLength(input.substring(i))
            count += findJsonNumberValue(input.substring(i),len)
            i += len-1
        }
        i++
    }
    return count
}

fun findJsonArrayValue(input: String, length: Int): Int {
    var count = 0
    var i = 0
    while(i < length) {  		// length includes }
        if(input[i] == '{') {
            val len = findJsonObjectLength(input.substring(i + 1))
            count += findJsonObjectValue(input.substring(i + 1),len)
            i += len
        } else if(input[i] == '[') {
            val len = findJsonArrayLength(input.substring(i+1))
            count += findJsonArrayValue(input.substring(i+1),len)
            i += len
        } else if(input[i] == '"') {
            val len = findJsonStrLength(input.substring(i+1))
            i += len
        } else if(input[i] in '0' .. '9' || input[i] == '-') {
            val len = findJsonNumberLength(input.substring(i))
            count += findJsonNumberValue(input.substring(i),len)
            i += len-1
        }
        i++
    }
    return count
}

fun findJsonStrValue(input: String, length: Int): String {
    var str = ""
    var i = 0
    while(i < length-1) {  // discludes the end quote
        str += input[i]
        i++
    }
    return str
}

fun findJsonNumberValue(input: String, length: Int): Int {
    var num = ""
    var i = 0
    while(i < length) {  // discludes the end quote
        num += input[i]
        i++
    }
    val number = num.toIntOrNull()
    if(number != null) return number
    else return 0
}


/* length */

fun findJsonObjectLength(input: String): Int {
    var i = 0
    var openBraces = 1
    while(openBraces > 0) {
        if(input[i] == '{') {
            openBraces++
        } else if(input[i] == '}') {
            openBraces--
        }
        i++
    }
    return i
}

fun findJsonArrayLength(input: String): Int {
    var i = 0
    var openBrackets = 1
    while(openBrackets > 0) {
        if(input[i] == '[') {
            openBrackets++
        } else if(input[i] == ']') {
            openBrackets--
        }
        i++
    }
    return i
}

fun findJsonStrLength(input: String): Int {
    var i = 0
    while(input[i] != '"') {
        i++
    }
    return i + 1  	// inlcudes the end quote
}


fun findJsonNumberLength(input: String): Int {
    var i = 0
    while(input[i] in '0' .. '9' || input[i] == '-') {
        i++
    }
    return i
}




