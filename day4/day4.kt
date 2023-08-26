import java.math.BigInteger
import java.security.MessageDigest

fun main(args: Array<String>) {
    
    if(args.isEmpty()) {
        System.err.println("Secret key expected as command line argument.")
        return
    }
    
    val secretKey: String = args[0]
    var number = 1
    while(true) {
        val md5Input = secretKey + number.toString()
        val MD5Hash = calculateMD5(md5Input)
        if(hasLeadingZeros(MD5Hash,5)) {
            break
        }
        number++
    }

    println("MD5 Input Part 1: $number$secretKey")
    
    while(true) {
        val md5Input = secretKey + number.toString()
        val MD5Hash = calculateMD5(md5Input)
        if(hasLeadingZeros(MD5Hash,6)) {
            break
        }
        number++
    }

    println("MD5 Input Part 2: $number$secretKey")
}


fun calculateMD5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    val messageDigest = md.digest(input.toByteArray())
    val no = BigInteger(1, messageDigest)
    var hashText = no.toString(16)
    while (hashText.length < 32) {
        hashText = "0$hashText"
    }
    return hashText
}


fun hasLeadingZeros(input: String, neededLeadingNumbers: Int): Boolean {
    val leadingNumbers = input.substring(0,neededLeadingNumbers)
    leadingNumbers.forEach {
        if(!it.equals('0')) {
            return false
        }
    }
    return true
}
