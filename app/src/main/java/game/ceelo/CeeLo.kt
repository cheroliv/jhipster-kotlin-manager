package game.ceelo

import game.ceelo.DiceThrowResult.*

enum class DiceThrowResult {
    WIN, LOOSE, RETHROW
}

/**
 * un jet de dés au hazard
 */
val dicesThrow get() = List(size = 3, init = { (ONE..SIX).random() })

/**
 * La valeur du deuxieme dé
 */
fun <T> List<T>.middle(): T {
    if (isEmpty())
        throw NoSuchElementException("dice throw is empty.")
    return this.elementAt(index = 1)
}

/**
 * Est ce que le jet est un 1 2 3 ?
 */
fun List<Int>.isOpponentMade123(secondPlayerThrow: List<Int>): DiceThrowResult =
    if (containsAll(`1_2_3`) &&
        !secondPlayerThrow.containsAll(`1_2_3`)
    ) LOOSE else if (containsAll(`1_2_3`) &&
        secondPlayerThrow.containsAll(`1_2_3`)
    ) RETHROW else WIN


/**
 * Est ce que le jet est un 4 5 6 ?
 */
fun List<Int>.isOpponentMade456(secondPlayerThrow: List<Int>): DiceThrowResult =
    if (containsAll(`4_5_6`) &&
        !secondPlayerThrow.containsAll(`4_5_6`)
    ) WIN else if (containsAll(`4_5_6`) &&
        secondPlayerThrow.containsAll(`4_5_6`)
    ) RETHROW else LOOSE

/**
 * La valeur faciale du dé triplet
 * Si le jet n'est pas un triplet
 * renvoi NOT_A_TRIPLET
 */
val List<Int>.whichTripletIsIt: Int
    get() = if (!isTriplet) NOT_A_TRIPLET
    else TRIPLETS.find { it.containsAll(elements = this) }!!.first()

val List<Int>.whichThrowBranch: Int
    get() {
        if (containsAll(`4_5_6`)) return 1
        if (containsAll(`1_2_3`)) return 2
        if (isTriplet) return 3
        if (isDoublet()) return 4
        return 5
    }

fun List<Int>.isDoublet(): Boolean {
    TODO("Not yet implemented")
}
/**
 * Est ce un triplet?
 */
val List<Int>.isTriplet: Boolean
    get() {
        return TRIPLETS.map { it.containsAll(this) }.contains(true)
    }


val List<Int>.is456: Boolean
    get() {
        TODO("Not yet implemented")
    }


val List<Int>.is123: Boolean
    get() {
        TODO("Not yet implemented")
    }

/**
 * compare un jet à un autre
 * pour renvoyer un resultat de jeu
 */
//fun List<Int>.compareThrows(secondPlayerThrow: List<Int>): DiceThrowResult {
//    if (containsAll(`4_5_6`)) return isOpponentMade456(secondPlayerThrow)
//    if (containsAll(`1_2_3`)) return isOpponentMade123(secondPlayerThrow)
//}
