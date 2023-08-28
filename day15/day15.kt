
import java.io.File
import java.io.InputStream


val kitchenIngredients = mutableListOf<Ingredient>()
var highestCookieScore = Int.MIN_VALUE
var highestCookieScoreWith500Calories = Int.MIN_VALUE

fun main() {

    var inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { str ->
        addIngredient(str)
    }

    val teaspoonsOfIngredients = 100
    val numberOfIngredients = kitchenIngredients.size
    generateIngredientCombinations(teaspoonsOfIngredients, numberOfIngredients)

    println("Highest cookie score: $highestCookieScore")
    println("Highest cookies score (500 Cal): $highestCookieScoreWith500Calories")
}

fun generateIngredientCombinations(totalTeaspoons: Int, numberOfIngredients: Int) {
    val ingredients = IntArray(numberOfIngredients)
    generateCombinations(totalTeaspoons, numberOfIngredients, ingredients, 0)
}

fun generateCombinations(totalTeaspoons: Int, numberOfIngredients: Int, ingredientTeaspoons: IntArray, index: Int) {
    if (index == numberOfIngredients) {
        if (ingredientTeaspoons.sum() == totalTeaspoons) {
            calculateCookieScore(ingredientTeaspoons)
        }
        return
    }
    for (i in 0..totalTeaspoons) {
        ingredientTeaspoons[index] = i
        generateCombinations(totalTeaspoons, numberOfIngredients, ingredientTeaspoons, index + 1)
    }
}

fun calculateCookieScore(ingredientTeaspoons: IntArray) {
    val propertyTotals = IntArray(5)
    for(i in 0 until kitchenIngredients.size) {
        propertyTotals[0] += ingredientTeaspoons[i] * kitchenIngredients[i].capacity
        propertyTotals[1] += ingredientTeaspoons[i] * kitchenIngredients[i].durability
        propertyTotals[2] += ingredientTeaspoons[i] * kitchenIngredients[i].flavor
        propertyTotals[3] += ingredientTeaspoons[i] * kitchenIngredients[i].texture
        propertyTotals[4] += ingredientTeaspoons[i] * kitchenIngredients[i].calories
    }
    for(i in 0 until propertyTotals.size) if(propertyTotals[i] < 0) propertyTotals[i] = 0
    val is500Calories = propertyTotals[4] == 500
    val totalScore = propertyTotals[0] * propertyTotals[1] * propertyTotals[2] * propertyTotals[3]
    if(totalScore > highestCookieScore) highestCookieScore = totalScore
    if(totalScore > highestCookieScoreWith500Calories && is500Calories) highestCookieScoreWith500Calories = totalScore
}

fun addIngredient(inputString: String) {
    val splitInput = inputString.split(" ")
    val ingredientName = splitInput[0].substring(0,splitInput[0].length-1)
    val capacity = splitInput[2].substring(0,splitInput[2].length-1).toInt()
    val durability = splitInput[4].substring(0,splitInput[4].length-1).toInt()
    val flavor = splitInput[6].substring(0,splitInput[6].length-1).toInt()
    val texture = splitInput[8].substring(0,splitInput[8].length-1).toInt()
    val calories = splitInput[10].toInt()
    kitchenIngredients.add(Ingredient(ingredientName, capacity, durability, flavor, texture, calories))
}

class Ingredient(ing: String, cap: Int, dur: Int, flav: Int, text: Int, cal: Int) {
    val name = ing
    val capacity = cap
    val durability = dur
    val flavor = flav
    val texture = text
    val calories = cal
}
