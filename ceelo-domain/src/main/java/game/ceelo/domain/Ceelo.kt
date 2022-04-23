package game.ceelo.domain


import game.ceelo.domain.DiceThrowResult.*

enum class DiceThrowResult {
    WIN, LOOSE, RETHROW
}

fun main() {
    println("un jet de dés :")
    println(dicesThrow)
}

/**
 * un jet de dés au hazard
 */
val dicesThrow: List<Int> by lazy { List(size = 3, init = { (ONE..SIX).random() }) }

/**
 * Est ce un triplet?
 */
val List<Int>.isUniformTriplet: Boolean
    get() = UNIFORM_TRIPLETS.map { it.containsAll(elements = this) }.contains(true)

val List<Int>.is456: Boolean get() = containsAll(`4_5_6`)

val List<Int>.is123: Boolean get() = containsAll(`1_2_3`)

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
val List<Int>.uniformTripletValue: Int
    get() = if (!isUniformTriplet) NOT_A_TRIPLET
    else UNIFORM_TRIPLETS.find { it.containsAll(elements = this) }!!.first()

val List<Int>.whichThrowBranch: Int
    get() {
        if (containsAll(`4_5_6`)) return 1
        if (containsAll(`1_2_3`)) return 2
        if (isUniformTriplet) return 3
        if (isDoublet) return 4
        return 5
    }

val List<Int>.isDoublet: Boolean
    get() = UNIFORM_DOUBLETS.map { it.containsAll(elements = this) }.contains(true)


/**
 * compare un jet à un autre
 * pour renvoyer un resultat de jeu
 */
//fun List<Int>.compareThrows(secondPlayerThrow: List<Int>): DiceThrowResult {
//    if (containsAll(`4_5_6`)) return isOpponentMade456(secondPlayerThrow)
//    if (containsAll(`1_2_3`)) return isOpponentMade123(secondPlayerThrow)
//}
