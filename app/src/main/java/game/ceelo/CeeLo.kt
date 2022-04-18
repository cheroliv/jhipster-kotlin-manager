package game.ceelo

import game.ceelo.DiceThrowResult.*

const val ONE = 1
const val TWO = 2
const val THREE = 3
const val FOUR = 4
const val FIVE = 5
const val SIX = 6
const val CEELO_DICE_THROW_SIZE = 3

@Suppress("ObjectPropertyName")
val `4_5_6` by lazy { listOf(FOUR, FIVE, SIX) }

@Suppress("ObjectPropertyName")
val `1_2_3` by lazy { listOf(ONE, TWO, THREE) }

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
    return if (containsAll(`4_5_6`) &&
        !secondPlayerThrow.containsAll(`4_5_6`)
    ) WIN else if (containsAll(`4_5_6`) &&
        secondPlayerThrow.containsAll(`4_5_6`)
    ) RETHROW else LOOSE
}