package game.ceelo


import game.ceelo.CeeloGameDomain.compareThrows
import game.ceelo.CeeloGameDomain.randomNumberOfPlayers
import game.ceelo.CeeloPlaygroundDomain.launchGame
import game.ceelo.CeeloPlaygroundDomain.launchLocalGame
import game.ceelo.DiceRunResult.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CeeloMudUnitTest {
    /*
        - lancer une partie entre 2 et 6 joueurs
        -
     */
    @Test
    fun random_nombre_de_joueurs_est_inf_ou_egal_a_6_et_sup_ou_egal_a_2() {
        assert(randomNumberOfPlayers() <= SIX)
        assert(randomNumberOfPlayers() >= TWO)
    }


    @Test
    fun `launchLocalGame renvoi un game multi joueurs`() {
        val numberOfPlayer = randomNumberOfPlayers()
        val result = launchLocalGame(numberOfPlayer)
        println(numberOfPlayer)
        println(result)
        assertEquals(numberOfPlayer, result.size)
        result.map { hands ->
            assertEquals(THREE, hands.size)
            hands.map { dice -> assert(dice in ONE..SIX) }
        }
    }

    @Test
    fun `compareRuns renvoi la liste des vainqueurs d'une partie`() {
        val numberOfPlayer: Int = randomNumberOfPlayers()
        val result: List<List<Int>> = launchLocalGame(numberOfPlayer)
        println(numberOfPlayer)
        println(result)
        assertEquals(numberOfPlayer, result.size)

        var winnerIndexes: MutableList<Int> = mutableListOf(0)
        result.forEachIndexed { index, hands: List<Int> ->
            if (index > 0) {
                if (hands.compareThrows(result[index - 1]) == WIN)
                    winnerIndexes[0] = index
                if (
                    hands.compareThrows(result[index - 1]) == RETHROW
                    && hands.compareThrows(result.first()) == WIN
                ) winnerIndexes.add(index)
            }
        }
        println("winner: ${winnerIndexes.map { result[it] }}")

        //construisons des situations facile a tester
//        val strongerFirst=
    }

}