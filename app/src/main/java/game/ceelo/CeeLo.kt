@file:Suppress("ObjectPropertyName")

package game.ceelo

import game.ceelo.DiceThrowResult.*

const val ONE = 1
const val TWO = 2
const val THREE = 3
const val FOUR = 4
const val FIVE = 5
const val SIX = 6
const val CEELO_DICE_THROW_SIZE = 3

val `4_5_6` by lazy { listOf(FOUR, FIVE, SIX) }
val `1_2_3` by lazy { listOf(ONE, TWO, THREE) }

val `1_1_1` by lazy { listOf(ONE, ONE, ONE) }
val `2_2_2` by lazy { listOf(TWO, TWO, TWO) }
val `3_3_3` by lazy { listOf(THREE, THREE, THREE) }
val `4_4_4` by lazy { listOf(FOUR, FOUR, FOUR) }
val `5_5_5` by lazy { listOf(FIVE, FIVE, FIVE) }
val `6_6_6` by lazy { listOf(SIX, SIX, SIX) }

val TRIPLETS by lazy {
    listOf(
        `1_1_1`, `2_2_2`, `3_3_3`,
        `4_4_4`, `5_5_5`, `6_6_6`
    )
}
const val NOT_A_TRIPLET = -1
const val NOT_A_DOUBLET = 0


fun <T> List<T>.middle(): T {
    if (isEmpty())
        throw NoSuchElementException("dice throw is empty.")
    return this.elementAt(index = 1)
}

val dicesThrow: List<Int>
    get() = List(size = 3) { (ONE..SIX).random() }


enum class DiceThrowResult {
    WIN, LOOSE, RETHROW
}

fun List<Int>.evalThrows(secondPlayerThrow: List<Int>)
        : DiceThrowResult = if (containsAll(`4_5_6`))
    diceThrowResultIs456(secondPlayerThrow)
else diceThrowResult123(secondPlayerThrow)

private fun List<Int>.diceThrowResult123(secondPlayerThrow: List<Int>)
        : DiceThrowResult = if (containsAll(`1_2_3`) &&
    !secondPlayerThrow.containsAll(`1_2_3`)
) LOOSE else if (containsAll(`1_2_3`) &&
    secondPlayerThrow.containsAll(`1_2_3`)
) RETHROW else WIN

private fun List<Int>.diceThrowResultIs456(secondPlayerThrow: List<Int>)
        : DiceThrowResult = if (containsAll(`4_5_6`) &&
    !secondPlayerThrow.containsAll(`4_5_6`)
) WIN else if (containsAll(`4_5_6`) &&
    secondPlayerThrow.containsAll(`4_5_6`)
) RETHROW else LOOSE

fun isTriplet(param: List<Int>): Boolean =
    TRIPLETS.map { it.containsAll(param) }.contains(true)

fun wichTriplet(currentDiceThrow: List<Int>)
        : Int = if (!isTriplet(currentDiceThrow))
    NOT_A_TRIPLET
else TRIPLETS.find {
    it.containsAll(currentDiceThrow)
}!!.first()