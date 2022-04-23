package game.ceelo.domain

/**
 * un jet de dés au hazard
 */
fun getDicesThrow(): List<Int> = List(size = 3, init = { (ONE..SIX).random() })

/**
 * Renvoi le dé du milieu
 */
fun List<Int>.middle(): Int {
    if (isEmpty())
        throw NoSuchElementException("dice throw is empty.")
    return this.elementAt(index = 1)
}

/**
 * Est ce un triplet?
 */
val List<Int>.containsUniformTriplet: Boolean
    get() = UNIFORM_TRIPLETS.map {
        it.first().run {
            this == first() && this == middle() && this == last()
        }
    }.contains(true)

val List<Int>.is456: Boolean get() = containsAll(`4_5_6`)

val List<Int>.is123: Boolean get() = containsAll(`1_2_3`)

val List<Int>.containsUniformDoublet: Boolean
    get() = run {
        UNIFORM_DOUBLETS.map {
            it.first().run {
                first() == this && middle() == this && last() != this ||
                        first() == this && last() == this && middle() != this ||
                        middle() == this && last() == this && first() != this
            }
        }.contains(true)
    }

/**
 * La valeur faciale du dé triplet
 * Si le jet n'est pas un triplet
 * renvoi NOT_A_TRIPLET
 */
val List<Int>.uniformTripletValue: Int
    get() = if (!containsUniformTriplet) NOT_A_TRIPLET
    else UNIFORM_TRIPLETS.find { containsAll(elements = it) }!!.first()

/**
 * Renvoi la valeur du dé qui n'est pas un doublet
 */
val List<Int>.uniformDoubletValue: Int
    get() = if (!containsUniformTriplet && !containsUniformDoublet) NOT_A_DOUBLET
    else {
        when {
            containsUniformTriplet -> NOT_A_DOUBLET
            containsUniformDoublet ->
                find { it ->
                    UNIFORM_DOUBLETS.first {
                        first() == it.first() && middle() == it.first() ||
                                first() == it.first() && last() == it.first() ||
                                middle() == it.first() && last() == it.first()
                    }.first() != it
                }!!
            else -> NOT_A_DOUBLET
        }
    }


fun main() {
    //"ici dans ce main c'est le playground pour tester du code"
    println("un jet de dés :")
    println("bank throw : ${getDicesThrow()}")
    println("player one throw : ${getDicesThrow()}")
    println("player two throw : ${getDicesThrow()}")
    println("player three throw : ${getDicesThrow()}")

    val doublet = listOf(1, 5, 1)
    println("doublet : $doublet")

    println()
}


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
