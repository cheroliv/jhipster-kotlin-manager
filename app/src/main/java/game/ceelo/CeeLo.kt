package game.ceelo

import game.ceelo.DiceThrowResult.*

enum class DiceThrowResult {
    WIN, LOOSE, RETHROW
}

fun <T> List<T>.middle(): T {
    if (isEmpty())
        throw NoSuchElementException("dice throw is empty.")
    return this.elementAt(index = 1)
}

val dicesThrow: List<Int>
    get() = List(size = 3) { (ONE..SIX).random() }


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
    TRIPLETS
        .map { it.containsAll(param) }
        .contains(true)


fun whichTriplet(currentDiceThrow: List<Int>): Int =
    if (!isTriplet(currentDiceThrow))
        NOT_A_TRIPLET
    else TRIPLETS.find {
        it.containsAll(currentDiceThrow)
    }!!.first()