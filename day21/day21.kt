import java.io.File
import java.io.InputStream

fun main() {
    val bossStats = mutableListOf<Int>()
    var inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { str ->
        bossStats.add(str.split(": ")[1].toInt())
    }
    val boss = Player(bossStats[0],bossStats[1],bossStats[2])
    val winningPlayers = findWinningPlayers(boss)
    val losingPlayers = findLosingPlayers(boss)
    val shopItems = getShopItems()

    println("${costOfCheapestWinner(winningPlayers,shopItems)} gold is the least that can be spend to win.")
    println("${costOfMostExpensiveLoser(losingPlayers,shopItems)} gold is the most that can be spent to still lose.")
}

class Player(hp: Int, dmg: Int, arm: Int) {
    val hitPoints = hp
    val damage = dmg
    val armor = arm
}

fun findWinningPlayers(boss: Player): List<Player> {
    val winningPlayers = mutableListOf<Player>()
    for(damage in 4 .. 13) {
        for(armor in 0 .. 10) {
            val player = Player(100,damage,armor)
            if(canPlayerWinBattle(player,boss)) winningPlayers.add(player)
        }
    }
    return winningPlayers
}

fun findLosingPlayers(boss: Player): List<Player> {
    val losingPlayers = mutableListOf<Player>()
    for(damage in 4 .. 13) {
        for(armor in 0 .. 10) {
            val player = Player(100,damage,armor)
            if(!canPlayerWinBattle(player,boss)) losingPlayers.add(player)
        }
    }
    return losingPlayers
}

fun canPlayerWinBattle(player: Player, boss: Player): Boolean {
    val playerAttack = if(player.damage > boss.armor) player.damage - boss.armor else 1
    var playerHP = player.hitPoints
    val bossAttack = if(boss.damage > player.armor) boss.damage - player.armor else 1
    var bossHP = boss.hitPoints

    var turn = 1
    while(true) {
        if(turn % 2 == 1) { // player attcks
            bossHP -= playerAttack
            if(bossHP <= 0) return true
        } else { //boss attacks
            playerHP -= bossAttack
            if(playerHP <= 0) return false
        }
        turn++
    }
}

fun costOfCheapestWinner(winningPlayers: List<Player>, shopItems: List<Item>): Int {
    var lowestCost = Int.MAX_VALUE
    for(player in winningPlayers) {
        val validCombinations = findValidItemCombinations(player,shopItems)
        if(validCombinations.isEmpty()) continue
        val cheapestCombo = validCombinations.minBy { it.totalCost() }
        if(cheapestCombo.totalCost() < lowestCost) lowestCost = cheapestCombo.totalCost()
    }
    return lowestCost
}

fun costOfMostExpensiveLoser(losingPlayers: List<Player>, shopItems: List<Item>): Int {
    var highestCost = 0
    for(player in losingPlayers) {
        val validCombinations = findValidItemCombinations(player,shopItems)
        if(validCombinations.isEmpty()) continue
        val mostExpensiveCombo = validCombinations.maxBy { it.totalCost() }
        if(mostExpensiveCombo.totalCost() > highestCost) highestCost = mostExpensiveCombo.totalCost()
    }
    return highestCost
}

sealed class Item() {
    
	data class Weapon(
        val name: String,
        val cost: Int,
        val damage: Int
    ) : Item()

    data class Armor(
        val name: String,
        val cost: Int,
        val armor: Int
    ) : Item()

    data class Ring(
        val name: String,
        val cost: Int,
        val damage: Int,
        val armor: Int
    ) : Item()
}

fun getShopItems(): List<Item> = listOf(
    Item.Weapon("Dagger",8,4),
    Item.Weapon("Shortsword",10,5),
    Item.Weapon("Warhammer",25,6),
    Item.Weapon("Longsword",40,7),
    Item.Weapon("Greataxe",74,8),
    Item.Armor("Leather", 13,1),
    Item.Armor("Chainmail",31,2),
    Item.Armor("Splintmail",53,3),
    Item.Armor("Bandedmail",75,4),
    Item.Armor("Platemail",102,5),
    Item.Ring("Damage +1",25,1,0),
    Item.Ring("Damage +2",50,2,0),
    Item.Ring("Damage +3",100,3,0),
    Item.Ring("Defense +1",20,0,1),
    Item.Ring("Defense +2",40,0,2),
    Item.Ring("Defense +3",80,0,3)
)


data class ItemCombination (
    val weapon: Item.Weapon?,
    val armor: Item.Armor?,
    val rings: List<Item.Ring>
) {
    fun hasWeapon(): Boolean = weapon != null
    fun hasArmor(): Boolean = armor != null
    fun ringCount(): Int = rings.size
    fun totalCost(): Int {
        val weaponCost = if(weapon != null) weapon.cost else 0
        val armorCost = if(armor != null) armor.cost else 0
        val ringsCost = if(!rings.isEmpty()) rings.sumOf { it.cost } else 0
        return weaponCost + armorCost + ringsCost
    }
}

fun findValidItemCombinations(player: Player, shopItems: List<Item>): List<ItemCombination> {
    val validCombinations = mutableListOf<ItemCombination>()

    fun findCombinations(
        currentIndex: Int,
        remainingDamage: Int,
        remainingArmor: Int,
        combination: ItemCombination
    ) {
        if (currentIndex == shopItems.size) {
            if (remainingDamage == 0 && remainingArmor == 0 && combination.hasWeapon()) {
                validCombinations.add(combination)
            }
            return
        }

        val currentItem = shopItems[currentIndex]
        val nextIndex = currentIndex + 1

        // Not using current item
        findCombinations(
            nextIndex,
            remainingDamage,
            remainingArmor,
            combination
        )

        // Use ring if combination less than two rings
        if (currentItem is Item.Ring && combination.ringCount() < 2) {
            val newCombination = combination.copy(rings = combination.rings + currentItem)
            findCombinations(
                nextIndex,
                remainingDamage - currentItem.damage,
                remainingArmor - currentItem.armor,
                newCombination
            )
        // Use armor if combination doesn't have armor
        } else if (currentItem is Item.Armor && !combination.hasArmor()) {
            val newArmor = combination.armor?.armor ?: 0 + currentItem.armor
            val newCombination = combination.copy(armor = currentItem.copy(armor = newArmor))
            findCombinations(
                nextIndex,
                remainingDamage,
                remainingArmor - currentItem.armor,
                newCombination
            )
        // Use weapon if combination doesn't have a weapon
        } else if (currentItem is Item.Weapon && !combination.hasWeapon()) {
            val newCombination = combination.copy(weapon = currentItem)
            findCombinations(
                nextIndex,
                remainingDamage - currentItem.damage,
                remainingArmor,
                newCombination
            )
        }
    }

    val startingCombination = ItemCombination(null, null, emptyList())
    findCombinations(0, player.damage, player.armor, startingCombination)

    return validCombinations
}
