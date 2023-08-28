import java.math.BigInteger
import java.security.MessageDigest

fun main(args: Array<String>) {

    if (args.size != 1) {
        System.err.println("Usage: [old password] expected as command line arguments.")
        return
    }

    val oldPassword: String = args[0]
    var password = oldPassword

    while(true) {
        if(isValidPassword(password)) break
        password = incrementPassword(password)
    }

    println("The new password is $password.")

    password = incrementPassword(password)
    while(true) {
        if(isValidPassword(password)) break
        password = incrementPassword(password)
    }

    println("The new password is $password.")

}

fun isValidPassword(password: String): Boolean {
    return hasThreeLetterStraight(password)
            && hasNoConfusingLetters(password)
            && hasTwoLetterPairs(password)
}

fun hasThreeLettersStraight(password: String): Boolean {
    for(i in 0 until password.length-2)
        if(password[i+2] == password[i+1]+1 &&
            password[i+1] == password[i]+1) return true
    return false
}

fun hasNoConfusingLetters(password: String): Boolean {
    for(i in 0 until password.length)
        if(password[i] == 'i' || password[i] == 'j'
            || password[i] == 'l') return false
    return true
}

fun hasTwoLetterPairs(password: String): Boolean {
    var pairCount = 0
    var previousPair = false
    for(i in 0 until password.length-1) {
        if(previousPair) {
            previousPair = false
            continue
        }
        if(password[i] == password[i+1]) {
            pairCount++
            previousPair = true
        }
    }
    return pairCount >= 2
}

fun incrementPassword(oldPassword: String): String {
    var newPassword = ""
    var increment = true
    for(i in (0 until oldPassword.length).reversed()) {
        var letter: Char = oldPassword[i]
        if(increment) letter++
        if(letter > 'z') {
            increment = true
            letter = 'a'
        } else increment = false
        newPassword = letter + newPassword
    }
    if(increment) newPassword = 'a' + newPassword
    return newPassword
}
