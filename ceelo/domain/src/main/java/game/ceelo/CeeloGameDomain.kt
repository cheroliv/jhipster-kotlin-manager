package game.ceelo

import game.ceelo.CeeloDicesHandDomain.is123
import game.ceelo.CeeloDicesHandDomain.is456
import game.ceelo.CeeloDicesHandDomain.isStraight
import game.ceelo.CeeloDicesHandDomain.isUniformDoublet
import game.ceelo.CeeloDicesHandDomain.isUniformTriplet
import game.ceelo.CeeloDicesHandDomain.uniformDoubletValue
import game.ceelo.CeeloDicesHandDomain.uniformTripletValue
import game.ceelo.DiceRunResult.*


object CeeloGameDomain {
    /**
     * un jet de dés au hazard
     */
    fun runDices(): List<Int> = List(size = 3, init = { (ONE..SIX).random() })

    fun randomNumberOfPlayers(): Int {
        return (TWO..SIX).random()
    }

    fun initBank(howMuchPlayer: Int): List<Int> = List(
        size = howMuchPlayer,
        init = { (ONE..SIX).random() }
    )

    fun List<List<Int>>.firstPlayer(): List<Int> = if (isEmpty())
        throw NoSuchElementException("first player throw is empty.")
    else first()


    fun List<List<Int>>.secondPlayer(): List<Int> = if (isEmpty())
        throw NoSuchElementException("second player throw is empty.")
    else elementAt(index = ONE)


    fun List<List<Int>>.thirdPlayer(): List<Int> = if (isEmpty())
        throw NoSuchElementException("third player throw is empty.")
    else elementAt(index = TWO)

    fun List<List<Int>>.fourthPlayer(): List<Int> = if (isEmpty())
        throw NoSuchElementException("fourth player throw is empty.")
    else elementAt(index = THREE)

    fun List<List<Int>>.fifthPlayer(): List<Int> = if (isEmpty())
        throw NoSuchElementException("fourth player throw is empty.")
    else elementAt(index = FOUR)

    fun List<List<Int>>.sixthPlayer(): List<Int> = if (isEmpty())
        throw NoSuchElementException("fourth player throw is empty.")
    else elementAt(index = FIVE)

    fun List<List<Int>>.compareRuns(): List<List<Int>> {
//        var winner = first()
//        forEachIndexed { index: Int, hand ->
//            if (index >= ONE) {
//                val result = hand.compareThrows(this[index - 1])
//                if (result == WIN) {
//                    winner = hand
//                }
//            }
//        }
//        return winner
     return listOf(listOf(0,0))
    }

    @Suppress("MemberVisibilityCanBePrivate")
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
    fun List<Int>.compareRuns(secondPlayerRun: List<Int>)
            : DiceRunResult =
        whichCase.run whichCase@{
            @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_AGAINST_NOT_NOTHING_EXPECTED_TYPE")
            secondPlayerRun.whichCase.run otherWhichCase@{
                return when {
                    this@whichCase > this@otherWhichCase -> WIN
                    this@whichCase < this@otherWhichCase -> LOOSE
                    else -> onSameCase(
                        secondPlayerThrow = secondPlayerRun,
                        throwCase = this@whichCase
                    )
                }
            }
        }

    @Suppress("MemberVisibilityCanBePrivate")
    fun List<Int>.onSameCase(
        secondPlayerThrow: List<Int>,
        throwCase: Int
    ): DiceRunResult = when (throwCase) {
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
}