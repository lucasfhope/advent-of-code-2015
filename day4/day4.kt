import java.math.BigInteger
import java.security.MessageDigest

fun main(args: Array<String>) {
    
    if(args.isEmpty()) {
        System.err.println("Secret key expected as command line argument.")
        return
    }

    /* loops until the secret key appended to a number
	   has an MD5 Hash with 5 leading zeros (part 1) */
    
    val secretKey: String = args[0]
    var number = 1
    
    while(true) {
        val inputString = secretKey + number.toString()
        val MD5Hash = calculateMD5(inputString)
        if(hasLeadingZeros(MD5Hash,5)) {
            break
        }
        number++
    }

    println("The lowest positive number (appended to the secret key) to produce a MD5 hash with at least 5 leading zeros is $number.")

    /* loops until the secret key appended to a number
	   has an MD5 Hash with 5 leading zeros (part 2) */
    
    while(true) {
        val inputString = secretKey + number.toString()
        val MD5Hash = calculateMD5(inputString)
        if(hasLeadingZeros(MD5Hash,6)) {
            break
        }
        number++
    }

    println("The lowest positive number (appended to the secret key) to produce a MD5 hash with at least 6 leading zeros is $number.")
}

/* calculateMD5() returns the 
   MD5 hash of the index string */

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

/* hasLeadingZeros() returns true if the input string 
   has the inputted number of leading zeros */

fun hasLeadingZeros(input: String, index: Int): Boolean {
    val first5 = input.substring(0,index)
    first5.forEach {
        if(!it.equals('0')) {
            return false
        }
    }
    return true
}
