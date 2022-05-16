package game.ceelo

import game.ceelo.CeeloService.Ceelo.SIX
import game.ceelo.CeeloService.Ceelo.TWO
import game.ceelo.CeeloService.Ceelo.launchGame
import game.ceelo.CeeloService.Ceelo.randomNumberOfPlayers
import kotlin.test.Test

class CeeloMudTest {
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