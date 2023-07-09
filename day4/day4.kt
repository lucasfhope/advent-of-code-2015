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
        
        val inputString = secretKey + number.toString()
        val MD5Hash = calculateMD5(inputString)
        
        if(has_LeadingZeros(MD5Hash,5)) {
            break
        }
        
        number++
    }

    println("The lowest positive number (appended to the secret key) to produce a MD5 hash with at least 5 leading zeros is $number.")   /* ckczppom117946 */

    while(true) {
        
        val inputString = secretKey + number.toString()
        val MD5Hash = calculateMD5(inputString)
        
        if(has_LeadingZeros(MD5Hash,6)) {
            break
        }
        
        number++
    }

    println("The lowest positive number (appended to the secret key) to produce a MD5 hash with at least 6 leading zeros is $number.")   /* ckczppom3938038 */
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

fun has_LeadingZeros(input: String, index: Int): Boolean {
    
    val first5 = input.substring(0,index)

    first5.forEach {
        if(!it.equals('0')) {
            return false
        }
    }

    return true
}