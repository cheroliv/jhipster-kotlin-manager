@file:Suppress("ObjectPropertyName", "MemberVisibilityCanBePrivate")

package game.ceelo

import game.ceelo.CeeloService.Ceelo.DiceRunResult.*
import game.ceelo.model.Game
import game.ceelo.model.Playground

/*

RULES

Game can be played by minimum 2 player and maximum 4 player.
On startup all user will get chance/turn to roll 1 dice. Highest dice roll wins bank.
If more than 1 player have highest number, players that have the highest number
must roll again to get the highest number. This must continue until it is established who has bank.
Then banker will put Cee Lo bucks in the bank as much as he wants (Not below than mandatory minimum).
All other player will bet as much as he wants.
Then banker get a chance to roll 3 dice. Banker can tap on PUSH (Don't know the exact word) before taking his turn:
Automatic Win: If the banker rolls 4-5-6, "triples" (all three dice show the same number), or a pair (of non-6s) with a 6 then he/she instantly wins all bets.
Automatic Loss: If the banker rolls 1-2-3, or a pair (of non-1s) with a 1, he/she instantly loses all bets (the players break the bank).
Set Point: If the banker rolls a pair and a single (2, 3, 4, or 5), then the single becomes the banker's "point." E.g. a roll of 2-2-4 gives the banker a point of 4. Note that you can not set a point of 1 or 6, as those would result in an automatic loss or win, respectively (see above).
Re-roll: If the dice don't show any of the above combinations, then the banker rolls again and keeps rolling until he/she gets an instant win or an instant loss, or sets a point.
Banker will get limited amount of time to play his turn. If Banker didn't play on that duration then he/she will be timeout and total bet amount will be distributed to remaining users (Because we don't want to put other users for long waiting).
In Automatic Win case banker will get all bet chips amount and new round will be started from step 4.
In Automatic Loss case user will gets as much money as he bets.
In Set Point case banker point will be noted and other user will get chance to roll 3 dice. The player wins with a 4-5-6, triple, or any point higher than the Banker's. They lose with a 1-2-3, or any point lower than the banker's. Player will get limited amount of time to play his turn. If player didn't play his turn on that duration then he/she will be timeout and banker will gets bucks which that player bet on startup (Because we don't want to put other users for long waiting).
If player get triple banker must have to pay double bucks then user has bet (There may be one case that banker don't have enough bucks to pay double). Next round will start from step 4.
If banker pressed PUSH before taking his turn, In case of Triples banker must have to give double bucks then user has bet. Next round will start from step 4.
If  banker press PUSH before taking his turn, In case of Tie banker will win and user have to bet amount bucks. Next round will start from step 4.
If banker haven't press PUSH before taking his turn, In case of Tie (no winner or loser) the banker and the player will get his bet amount back. Next round will start from step 4.
Player can stick with bank by betting same money which is in the bank. In that case only that user and banker will play next game. In this case PUSH rule will not work. All other user have to wait for the next round (We need to think for this case because player comes to play game from all around the world. Player will not like to wait. If only two player is playing the game then the other player will left this table and will find new table from the lobby. This is my thought it depends on you what you want. We will develop game accordingly). Next round will start from step 4.
If Banker gets 4,5,6 then banker will be able to take Cee Lo bucks from the bank to his game account or can add more Cee Lo bucks into bank. Also banker can left the game. If banker left then game will start from step 2 with remaining user.
If player gets 4,5,6 then player gets the bank along with all Cee Lo bucks. (If two player gets 4,5,6 in the same round then no change. Bank remains with the same person). And new round will start from step 4.
If banker lost all his money which is in the bank then game will start from step 2.
 */
interface CeeloService {

    fun launchLocalGame(): List<List<Int>>
    fun allGames(): List<List<List<Int>>>
    fun saveGame(newGame: List<List<Int>>)

    object Ceelo {
        const val PLAYER_ONE_NAME = "Player"
        const val PLAYER_TWO_NAME = "Computer"
        const val GAME_TYPE = "LOCAL"

        const val ONE = 1
        const val TWO = 2
        const val THREE = 3
        const val FOUR = 4
        const val FIVE = 5
        const val SIX = 6
        const val CEELO_DICE_THROW_SIZE = 3
        const val NOT_A_TRIPLET = -1
        const val NOT_A_DOUBLET = 0

        const val AUTOMATIC_WIN_456_CASE = 6
        const val UNIFORM_TRIPLET_CASE = 5
        const val UNIFORM_DOUBLET_CASE = 4
        const val STRAIGHT_234_345_CASE = 3
        const val OTHER_THROW_CASE = 2
        const val AUTOMATIC_LOOSE_123_CASE = 1

        val `4_5_6` by lazy { listOf(FOUR, FIVE, SIX) }
        val `1_2_3` by lazy { listOf(ONE, TWO, THREE) }

        val `1_1_1` by lazy { listOf(ONE, ONE, ONE) }
        val `2_2_2` by lazy { listOf(TWO, TWO, TWO) }
        val `3_3_3` by lazy { listOf(THREE, THREE, THREE) }
        val `4_4_4` by lazy { listOf(FOUR, FOUR, FOUR) }
        val `5_5_5` by lazy { listOf(FIVE, FIVE, FIVE) }
        val `6_6_6` by lazy { listOf(SIX, SIX, SIX) }

        val UNIFORM_TRIPLETS by lazy {
            listOf(
                `1_1_1`,
                `2_2_2`,
                `3_3_3`,
                `4_4_4`,
                `5_5_5`,
                `6_6_6`
            )
        }

        val `1_1_x` by lazy { listOf(ONE, ONE) }
        val `2_2_x` by lazy { listOf(TWO, TWO) }
        val `3_3_x` by lazy { listOf(THREE, THREE) }
        val `4_4_x` by lazy { listOf(FOUR, FOUR) }
        val `5_5_x` by lazy { listOf(FIVE, FIVE) }
        val `6_6_x` by lazy { listOf(SIX, SIX) }


        val UNIFORM_DOUBLETS by lazy {
            listOf(
                `1_1_x`,
                `2_2_x`,
                `3_3_x`,
                `4_4_x`,
                `5_5_x`,
                `6_6_x`
            )
        }

        val `2_3_4` by lazy { listOf(TWO, THREE, FOUR) }
        val `3_4_5` by lazy { listOf(THREE, FOUR, FIVE) }

        val STRAIGHT_TRIPLETS by lazy {
            listOf(
                `2_3_4`,
                `3_4_5`,
            )
        }

        enum class DiceRunResult {
            WIN, LOOSE, RETHROW
        }

        fun runConsoleLocalGame() {
            var playerOne: List<Int> = runDices()
            var playerTwo: List<Int> = runDices()
            do {
                println("player one throw : $playerOne")
                println("player two throw : $playerTwo")
                val result = playerOne.compareThrows(
                    secondPlayerThrow = playerTwo
                )
                if (result == WIN) println("player one : $WIN")
                else println("player two : $WIN")
            } while (result == RETHROW.apply {
                    playerOne = runDices()
                    playerTwo = runDices()
                })
        }

        fun initPlayground(
            @Suppress("UNUSED_PARAMETER") howMuchPlayer: Int
        ): Playground = Playground()


        fun initBank(howMuchPlayer: Int): List<Int> = List(
            size = howMuchPlayer,
            init = { (ONE..SIX).random() }
        )

        fun launchGame(): Game = Game()

        /**
         * un jet de dés au hazard
         */
        fun runDices(): List<Int> = List(size = 3, init = { (ONE..SIX).random() })


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

        val List<Int>.isStraight: Boolean
            get() = STRAIGHT_TRIPLETS.map { containsAll(it) }.contains(true)


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
                : DiceRunResult =
            whichCase.run whichCase@{
                @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_AGAINST_NOT_NOTHING_EXPECTED_TYPE")
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

        fun getDiceImageFromDiceValue(
            diceValue: Int,
            diceImages: List<Int>
        ): Int = when (diceValue) {
            ONE -> diceImages[ONE - 1]
            TWO -> diceImages[TWO - 1]
            THREE -> diceImages[THREE - 1]
            FOUR -> diceImages[FOUR - 1]
            FIVE -> diceImages[FIVE - 1]
            SIX -> diceImages[SIX - 1]
            else -> throw Exception("Only six faces is possible!")
        }
    }
}