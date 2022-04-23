package game.ceelo.domain




fun main() {
    println("un jet de dés :")
    println("bank throw : ${getDicesThrow()}")
    println("player one throw : ${getDicesThrow()}")
    println("player two throw : ${getDicesThrow()}")
    println("player three throw : ${getDicesThrow()}")
}

/**
 * un jet de dés au hazard
 */
fun getDicesThrow(): List<Int> = List(size = 3, init = { (ONE..SIX).random() })


/**
 * Est ce un triplet?
 */
val List<Int>.containsUniformTriplet: Boolean
    get() = UNIFORM_TRIPLETS.map { it.containsAll(elements = this) }.contains(true)

val List<Int>.is456: Boolean get() = containsAll(`4_5_6`)

val List<Int>.is123: Boolean get() = containsAll(`1_2_3`)

val List<Int>.containsUniformDoublet: Boolean
    get() = UNIFORM_DOUBLETS.map { it.containsAll(elements = this) }.contains(true)
/**
 * La valeur faciale du dé triplet
 * Si le jet n'est pas un triplet
 * renvoi NOT_A_TRIPLET
 */
val List<Int>.uniformTripletValue: Int
    get() = if (!containsUniformTriplet) NOT_A_TRIPLET
    else UNIFORM_TRIPLETS.find { it.containsAll(elements = this) }!!.first()

val List<Int>.uniformDoubletValue: Int
    get() {
        TODO("Not yet implemented")
    }



//enum class DiceThrowResult {
//    WIN, LOOSE, RETHROW
//}
//val List<Int>.whichThrowBranch: Int
//    get() {
//        if (containsAll(`4_5_6`)) return 1
//        if (containsAll(`1_2_3`)) return 2
//        if (containsUniformTriplet) return 3
//        if (containsUniformDoublet) return 4
//        return 5
//    }
///**
// * compare un jet à un autre
// * pour renvoyer un resultat de jeu
// */
//fun List<Int>.compareThrows(secondPlayerThrow: List<Int>): DiceThrowResult {
//    if (containsAll(`4_5_6`)) return isOpponentMade456(secondPlayerThrow)
//    if (containsAll(`1_2_3`)) return isOpponentMade123(secondPlayerThrow)
//}

///**
// * Est ce que le jet est un 1 2 3 ?
// */
//fun List<Int>.isOpponentMade123(secondPlayerThrow: List<Int>): DiceThrowResult =
//    if (containsAll(`1_2_3`) &&
//        !secondPlayerThrow.containsAll(`1_2_3`)
//    ) LOOSE else if (containsAll(`1_2_3`) &&
//        secondPlayerThrow.containsAll(`1_2_3`)
//    ) RETHROW else WIN
//
//
///**
// * Est ce que le jet est un 4 5 6 ?
// */
//fun List<Int>.isOpponentMade456(secondPlayerThrow: List<Int>): DiceThrowResult =
//    if (containsAll(`4_5_6`) &&
//        !secondPlayerThrow.containsAll(`4_5_6`)
//    ) WIN else if (containsAll(`4_5_6`) &&
//        secondPlayerThrow.containsAll(`4_5_6`)
//    ) RETHROW else LOOSE
