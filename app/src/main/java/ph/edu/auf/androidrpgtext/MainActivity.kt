package ph.edu.auf.androidrpgtext

import android.os.Parcel
import android.os.Parcelable
import kotlin.random.Random

class Player(private var health: Int, private val attackDamage: Int) {
    fun attack(enemy: Enemy) {
        val damageDealt: Int = Random.nextInt(attackDamage) + 1
        enemy.takeDamage(damageDealt)
        println("Player attacks for $damageDealt damage.")
    }

    fun takeDamage(damage: Int) {
        health -= damage
        println("Player takes $damage damage.")
    }

    val isAlive: Boolean
        get() = health > 0
}

class Enemy(private var health: Int, private val attackDamage: Int) : Parcelable {
    fun attack(player: Player) {
        val damageDealt: Int = Random.nextInt(attackDamage) + 1
        player.takeDamage(damageDealt)
        println("Enemy attacks for $damageDealt damage.")
    }

    fun takeDamage(damage: Int) {
        health -= damage
        println("Enemy takes $damage damage.")
    }

    val isAlive: Boolean
        get() = health > 0

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(health)
        parcel.writeInt(attackDamage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Enemy> {
        override fun createFromParcel(parcel: Parcel): Enemy {
            return Enemy(parcel)
        }

        override fun newArray(size: Int): Array<Enemy?> {
            return arrayOfNulls(size)
        }
    }
}

class Game {
    val player: Player = Player(100, 20)
    val enemy: Enemy = Enemy(80, 15)

    fun playTurn() {
        // Player's turn
        player.attack(enemy)

        when (Random.nextInt(3)) {
            0 -> enemy.attack(player)
            1 -> {

            }
            2 -> {
            }
        }

        if (!player.isAlive) {
            println("Player has been defeated. Game over!")
        } else if (!enemy.isAlive) {
            println("Enemy has been defeated. You win!")
        }
    }
}

object RPGGameApp {
    @JvmStatic
    fun main(args: Array<String>) {
        val game = Game()
        while (game.player.isAlive && game.enemy.isAlive) {
            game.playTurn()
        }
    }
}
