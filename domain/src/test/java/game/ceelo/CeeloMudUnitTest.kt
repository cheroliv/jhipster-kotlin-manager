package game.ceelo


import game.ceelo.CeeloGameDomain.randomNumberOfPlayers
import game.ceelo.CeeloPlaygroundDomain.launchGame
import kotlin.test.Test

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
    fun `launchGame renvoi un game`() {
        val numberOfPlayer = randomNumberOfPlayers()
        println(numberOfPlayer)
        println(launchGame(numberOfPlayer))
    }
}