package game.ceelo

import game.ceelo.DiceThrowResult.*

val dicesThrow: List<Int>
    get() = List(size = 3) { (ONE..SIX).random() }


fun <T> List<T>.middle(): T {
    if (isEmpty())
        throw NoSuchElementException("dice throw is empty.")
    return this.elementAt(index = 1)
}


fun List<Int>.compareThrows(secondPlayerThrow: List<Int>): DiceThrowResult =
    if (containsAll(`4_5_6`)) isOpponentMade456(secondPlayerThrow)
    else isOpponentMade123(secondPlayerThrow)


/**
 * Est ce que le jet est un 1 2 3 ?
 */
fun List<Int>.isOpponentMade123(secondPlayerThrow: List<Int>)
        : DiceThrowResult = if (containsAll(`1_2_3`) &&
    !secondPlayerThrow.containsAll(`1_2_3`)
) LOOSE else if (containsAll(`1_2_3`) &&
    secondPlayerThrow.containsAll(`1_2_3`)
) RETHROW else WIN

/**
 * Est ce que le jet est un 4 5 6 ?
 */
fun List<Int>.isOpponentMade456(secondPlayerThrow: List<Int>)
        : DiceThrowResult = if (containsAll(`4_5_6`) &&
    !secondPlayerThrow.containsAll(`4_5_6`)
) WIN else if (containsAll(`4_5_6`) &&
    secondPlayerThrow.containsAll(`4_5_6`)
) RETHROW else LOOSE

/**
 * Est ce un triplet?
 */
fun isTriplet(param: List<Int>): Boolean =
    TRIPLETS
        .map { it.containsAll(param) }
        .contains(true)

/**
 * La valeur faciale du dé triplet
 * Si le jet n'est pas un triplet
 * renvoi NOT_A_TRIPLET
 */
fun whichTripletIsIt(currentDiceThrow: List<Int>): Int =
    if (!isTriplet(currentDiceThrow))
        NOT_A_TRIPLET
    else TRIPLETS.find {
        it.containsAll(currentDiceThrow)
    }!!.first()