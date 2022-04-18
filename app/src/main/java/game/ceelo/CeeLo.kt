package game.ceelo

import game.ceelo.DiceThrowResult.*

const val ONE = 1
const val TWO = 2
const val THREE = 3
const val FOUR = 4
const val FIVE = 5
const val SIX = 6
const val CEELO_DICE_THROW_SIZE = 3

val FOUR_FIVE_SIX = listOf(FOUR, FIVE, SIX)
val ONE_TWO_THREE = listOf(ONE, TWO, THREE)

fun <T> List<T>.middle(): T {
    if (isEmpty())
        throw NoSuchElementException("dice throw is empty.")
    return this.elementAt(1)
}

fun throwDices(): List<Int> = listOf(
    (ONE..SIX).random(),
    (ONE..SIX).random(),
    (ONE..SIX).random()
)

enum class DiceThrowResult {
    WIN, LOOSE, RETHROW
}

fun List<Int>.evalThrows(secondPlayerThrow: List<Int>): DiceThrowResult {
    return if (containsAll(FOUR_FIVE_SIX) &&
        !secondPlayerThrow.containsAll(FOUR_FIVE_SIX)
    ) WIN else if (containsAll(FOUR_FIVE_SIX) &&
        secondPlayerThrow.containsAll(FOUR_FIVE_SIX)
    ) RETHROW else LOOSE
}

fun containsFourFiveSix(diceThrow: List<Int>): Boolean = true