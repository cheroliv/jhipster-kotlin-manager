package game.ceelo.domain

import game.ceelo.domain.DiceThrowResult.WIN

/**
 * un jet de dés au hazard
 */
val dicesThrow: List<Int>
    get() = List(size = 3, init = { (ONE..SIX).random() })

/**
 * Renvoi le dé du milieu
 */
fun List<Int>.middle(): Int {
    if (isEmpty())
        throw NoSuchElementException("dice throw is empty.")
    return this.elementAt(index = 1)
}

val List<Int>.is456: Boolean get() = containsAll(`4_5_6`)

val List<Int>.is123: Boolean get() = containsAll(`1_2_3`)

/**
 * Est ce un triplet?
 */
val List<Int>.isUniformTriplet: Boolean
    get() = UNIFORM_TRIPLETS.map {
        it.first().run {
            this == first() && this == middle() && this == last()
        }
    }.contains(true)

/**
 * La valeur faciale du dé triplet
 * Si le jet n'est pas un triplet
 * renvoi NOT_A_TRIPLET
 */
val List<Int>.uniformTripletValue: Int
    get() = if (!isUniformTriplet) NOT_A_TRIPLET
    else UNIFORM_TRIPLETS.find { containsAll(elements = it) }!!.first()


val List<Int>.isUniformDoublet: Boolean
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
 * Renvoi la valeur du dé qui n'est pas un doublet
 */
val List<Int>.uniformDoubletValue: Int
    get() = if (!isUniformTriplet && !isUniformDoublet) NOT_A_DOUBLET
    else when {
        isUniformTriplet -> NOT_A_DOUBLET
        isUniformDoublet ->
            find { it: Int ->
                UNIFORM_DOUBLETS.first {
                    first() == it.first() && middle() == it.first() ||
                            first() == it.first() && last() == it.first() ||
                            middle() == it.first() && last() == it.first()
                }.first() != it
            }!!
        else -> NOT_A_DOUBLET
    }

val List<Int>.isStraight: Boolean get() = STRAIGHT_TRIPLETS.map { containsAll(it) }.contains(true)


val List<Int>.whichCase: Int
    get() = when {
        is456 -> AUTOMATIC_WIN_456_CASE
        is123 -> AUTOMATIC_LOOSE_123_CASE
        isStraight -> STRAIGHT_234_345_CASE
        isUniformTriplet -> TRIPLET_CASE
        isUniformDoublet -> DOUBLET_CASE
        else -> OTHERS_CASE
    }

/**
 * compare un jet à un autre
 * pour renvoyer un resultat de jeu
 */
fun List<Int>.compareThrows(secondPlayerThrow: List<Int>): DiceThrowResult {
    return WIN
}


@Suppress("UNUSED_PARAMETER")
fun main(args:Array<String>) {
    /*"ici dans ce main c'est le playground pour tester du code"*/
    println("un jet de dés :")
    println("bank throw : $dicesThrow")
    println("player one throw : $dicesThrow")
    println("player two throw : $dicesThrow")
    println("player three throw : $dicesThrow")

//    val doublet = listOf(1, 5, 1)
//    println("doublet : $doublet")
//    val straight1 = listOf(2, 3, 4)
//    val straight2 = listOf(3, 4, 5)
//    println(straight1.containsAll(listOf(4, 3, 2)))
//    println(straight2.containsAll(listOf(5, 4, 3)))
//    println()
//    println()
//    println()
//    println(dicesThrow)
//    println(dicesThrow)
//    println(dicesThrow)
//    println(dicesThrow)
}

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
