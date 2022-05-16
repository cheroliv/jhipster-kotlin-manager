package game.ceelo


import game.ceelo.CeeloGameDomain.randomNumberOfPlayers
import game.ceelo.CeeloPlaygroundDomain.launchGame
import game.ceelo.CeeloPlaygroundDomain.launchLocalGame
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
        result.map { hand ->
            assertEquals(THREE, hand.size)
            hand.map { dice -> assert(dice in 2..6) }
        }
    }

    @Test
    fun `compareRuns renvoi le vainqueur d'une partie`() {
        val numberOfPlayer = randomNumberOfPlayers()
        val result = launchLocalGame(numberOfPlayer)
        println(numberOfPlayer)
        println(result)
        assertEquals(numberOfPlayer, result.size)
        result.forEach { hand: List<Int> ->
            assertEquals(THREE, hand.size)
            hand.forEach { dice: Int ->
                assertTrue(actual = dice in ONE..SIX)
            }
        }
    }

}