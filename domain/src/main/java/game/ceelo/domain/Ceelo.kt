package game.ceelo.domain

import game.ceelo.domain.DiceThrowResult.*
import java.net.*
import java.io.*
import kotlin.coroutines.CoroutineContext

/**
 * un jet de dés au hazard
 */
 val dicesThrow: List<Int>
    get() = List(size = 3, init = { (ONE..SIX).random() })


//suspend fun rollDicesService():List<Int>{
//    CoroutineContext(Dispatchers)
//    return dicesThrow
//}


/**
 * Renvoi le dé du milieu
 */
fun List<Int>.middle(): Int = if (isEmpty())
    throw NoSuchElementException("dice throw is empty.")
else elementAt(index = 1)

fun List<List<Int>>.second(): List<Int> = if (isEmpty())
    throw NoSuchElementException("second player throw is empty.")
else elementAt(index = 1)

fun List<List<Int>>.third(): List<Int> = if (isEmpty())
    throw NoSuchElementException("third player throw is empty.")
else elementAt(index = 2)

fun List<List<Int>>.fourth(): List<Int> = if (isEmpty())
    throw NoSuchElementException("fourth player throw is empty.")
else elementAt(index = 3)

fun List<List<Int>>.fifth(): List<Int> = if (isEmpty())
    throw NoSuchElementException("fourth player throw is empty.")
else elementAt(index = 4)

fun List<List<Int>>.sixth(): List<Int> = if (isEmpty())
    throw NoSuchElementException("fourth player throw is empty.")
else elementAt(index = 5)

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
        isUniformTriplet -> UNIFORM_TRIPLET_CASE
        isUniformDoublet -> UNIFORM_DOUBLET_CASE
        else -> OTHER_THROW_CASE
    }

/**
 * compare un jet à un autre
 * pour renvoyer un resultat de jeu
 */
fun List<Int>.compareThrows(secondPlayerThrow: List<Int>)
: DiceThrowResult =
    whichCase.run whichCase@{
        secondPlayerThrow.whichCase.run otherWhichCase@{
            return when {
                this@whichCase > this@otherWhichCase -> WIN
                this@whichCase < this@otherWhichCase -> LOOSE
                else -> onSameCase(
                    secondPlayerThrow = secondPlayerThrow,
                    throwCase = this@whichCase
                )
            }
        }
    }

fun List<Int>.onSameCase(
    secondPlayerThrow: List<Int>,
    throwCase: Int
): DiceThrowResult = when (throwCase) {
    AUTOMATIC_WIN_456_CASE -> RETHROW
    AUTOMATIC_LOOSE_123_CASE -> RETHROW
    STRAIGHT_234_345_CASE -> when {
        containsAll(`2_3_4`) && secondPlayerThrow.containsAll(`2_3_4`) -> RETHROW
        containsAll(`3_4_5`) && secondPlayerThrow.containsAll(`3_4_5`) -> RETHROW
        containsAll(`2_3_4`) && secondPlayerThrow.containsAll(`3_4_5`) -> LOOSE
        else -> WIN
    }
    UNIFORM_TRIPLET_CASE -> when {
        uniformTripletValue > secondPlayerThrow.uniformTripletValue -> WIN
        uniformTripletValue < secondPlayerThrow.uniformTripletValue -> LOOSE
        else -> RETHROW
    }
    UNIFORM_DOUBLET_CASE -> when {
        uniformDoubletValue > secondPlayerThrow.uniformDoubletValue -> WIN
        uniformDoubletValue < secondPlayerThrow.uniformDoubletValue -> LOOSE
        else -> RETHROW
    }
    else -> when {
        sum() > secondPlayerThrow.sum() -> WIN
        sum() < secondPlayerThrow.sum() -> LOOSE
        else -> RETHROW
    }
}

enum class DiceThrowResult {
    WIN, LOOSE, RETHROW
}

fun main(/*args: Array<String>*/) {
    /*"ici dans ce main c'est le playground pour tester du code"*/
    println("un jet de dés :")
    dicesThrow.run playerOne@{
        dicesThrow.run playerTwo@{
            do {
                println("player one throw : ${this@playerOne}")
                println("player two throw : ${this@playerTwo}")
                val result = this@playerOne
                    .compareThrows(secondPlayerThrow = this@playerTwo)
                if (result == WIN) println("player one : $WIN")
                else println("player two : $WIN")
            } while (result == RETHROW)
        }
    }
}