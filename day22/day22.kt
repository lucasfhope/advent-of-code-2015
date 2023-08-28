import java.io.File
import java.io.InputStream

fun main() {
    val bossStats = mutableListOf<Int>()
    var inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { str ->
        bossStats.add(str.split(": ")[1].toInt())
    }
    val boss = Boss(bossStats[0], bossStats[1])
    val wizard = Wizard()

    println("Minimum mana used: ${manaUsedDuringBossBattle(wizard.copy(),boss.copy(),false)}")
    println("Minimum mana used in hard mode ${manaUsedDuringBossBattle(wizard.copy(),boss.copy(),true)}")
}

fun manaUsedDuringBossBattle(wizard: Wizard, boss: Boss, hardMode: Boolean): Int  {
    var minimumManaUsed = Int.MAX_VALUE

    fun bossBattle(wizard: Wizard, boss: Boss, turn: Int, hardMode: Boolean) {

        fun bossBattleWon(wizard: Wizard) {
            if(wizard.manaUsed < minimumManaUsed) {
                minimumManaUsed = wizard.manaUsed
            }
        }

        if(wizard.manaUsed > minimumManaUsed) return

        var bossDamage = boss.damage
        val nextTurn = turn+1

        /* Start of turn effects */
        if(hardMode && turn % 2 == 1) {
            if(--wizard.hitPoints <= 0) {
                return
            }
        }
        if(wizard.shieldEffectTimer > 0) {
            bossDamage = if(bossDamage - 7 < 1) 1 else bossDamage - 7
            wizard.shieldEffectTimer--
        }
        if(wizard.poisonEffectTimer > 0) {
            boss.hitPoints -= 3
            wizard.poisonEffectTimer--
            if(boss.hitPoints <= 0) {
                bossBattleWon(wizard)
                return
            }
        }
        if(wizard.rechargeEffectTimer > 0) {
            wizard.manaRemaining += 101
            wizard.rechargeEffectTimer--
        }

        /* Player's Turn */
        if(turn % 2 == 1) {
            /* Wizard uses Magic Missile */
            if(wizard.manaRemaining >= 53) {
                val magicMissileBoss = boss.copy()
                val magicMissileWizard = wizard.copy()
                magicMissileBoss.hitPoints -= magicMissileWizard.magicMissile()
                if (magicMissileBoss.hitPoints <= 0) {
                    bossBattleWon(magicMissileWizard)
                } else {
                    bossBattle(magicMissileWizard, magicMissileBoss, nextTurn, hardMode)
                }
            }
            /* Wizard uses Drain */
            if(wizard.manaRemaining >= 73) {
                val drainBoss = boss.copy()
                val drainWizard = wizard.copy()
                drainBoss.hitPoints -= drainWizard.drain()
                if (drainBoss.hitPoints <= 0) {
                    bossBattleWon(drainWizard)
                } else {
                    bossBattle(drainWizard, drainBoss, nextTurn, hardMode)
                }
            }
            /* Wizard uses shield */
            if(wizard.shieldEffectTimer == 0 && wizard.manaRemaining >= 113) {
                val shieldBoss = boss.copy()
                val shieldWizard = wizard.copy()
                shieldBoss.hitPoints -= shieldWizard.shield()
                if (shieldBoss.hitPoints <= 0) {
                    bossBattleWon(shieldWizard)
                } else {
                    bossBattle(shieldWizard, shieldBoss, nextTurn, hardMode)
                }
            }
            /* Wizard uses Poison */
            if(wizard.poisonEffectTimer == 0 && wizard.manaRemaining >= 173) {
                val poisonBoss = boss.copy()
                val poisonWizard = wizard.copy()
                poisonBoss.hitPoints -= poisonWizard.poison()
                if (poisonBoss.hitPoints <= 0) {
                    bossBattleWon(poisonWizard)
                } else {
                    bossBattle(poisonWizard, poisonBoss, nextTurn, hardMode)
                }
            }
            /* Wizard uses Recharge */
            if(wizard.rechargeEffectTimer == 0 && wizard.manaRemaining >= 229) {
                val rechargeBoss = boss.copy()
                val rechargeWizard = wizard.copy()
                rechargeBoss.hitPoints -= rechargeWizard.recharge()
                if (rechargeBoss.hitPoints <= 0) {
                    bossBattleWon(rechargeWizard)
                } else {
                    bossBattle(rechargeWizard, rechargeBoss, nextTurn, hardMode)
                }
            }
        }

        /* Boss's Turn */
        else {
            wizard.hitPoints -= bossDamage
            if(wizard.hitPoints <= 0) return
            bossBattle(wizard.copy(),boss.copy(),nextTurn,hardMode)
        }
    }

    bossBattle(wizard.copy(),boss.copy(),1, hardMode)
    return minimumManaUsed
}

open class Character (
    var hitPoints: Int,
) {
    open fun copy(): Character {
        return Character(hitPoints)
    }
}

class Boss(
    hitPoints: Int,
    val damage: Int
): Character(hitPoints) {
    override fun copy(): Boss {
        return Boss(hitPoints, damage)
    }
}

class Wizard(): Character(hitPoints = 50) {
    var manaRemaining: Int = 500
    var manaUsed: Int = 0
    var shieldEffectTimer: Int = 0
    var poisonEffectTimer: Int = 0
    var rechargeEffectTimer: Int = 0
    fun magicMissile(): Int {
        manaUsed += 53
        manaRemaining -= 53
        return 4
    }
    fun drain(): Int {
        manaUsed += 73
        manaRemaining -= 73
        hitPoints += 2
        return 2
    }
    fun shield(): Int {
        manaUsed += 113
        manaRemaining -= 113
        shieldEffectTimer = 6
        return 0
    }
    fun poison(): Int {
        manaUsed += 173
        manaRemaining -= 173
        poisonEffectTimer = 6
        return 0
    }
    fun recharge(): Int {
        manaUsed += 229
        manaRemaining -= 229
        rechargeEffectTimer = 5
        return 0
    }
    override fun copy(): Wizard {
        val wizardCopy = Wizard()
        wizardCopy.hitPoints = this.hitPoints
        wizardCopy.manaRemaining = this.manaRemaining
        wizardCopy.manaUsed = this.manaUsed
        wizardCopy.shieldEffectTimer = this.shieldEffectTimer
        wizardCopy.poisonEffectTimer = this.poisonEffectTimer
        wizardCopy.rechargeEffectTimer = this.rechargeEffectTimer
        return wizardCopy
    }
}
