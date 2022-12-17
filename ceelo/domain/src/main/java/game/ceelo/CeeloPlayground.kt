package game.ceelo

import game.ceelo.CeeloConstant.TWO
import game.ceelo.CeeloHand.compareHands
import game.ceelo.CeeloGame.firstPlayer
import game.ceelo.CeeloGame.runDices
import game.ceelo.CeeloGame.secondPlayer
import game.ceelo.CeeloResult.RERUN
import game.ceelo.CeeloResult.WIN


object CeeloPlayground {
    fun launchLocalGame(nbPlayers: Int): List<List<Int>> =
        mutableListOf<List<Int>>().apply {
            repeat(nbPlayers) { add(runDices()) }
        }.toList()

    fun launchLocalGame(): List<List<Int>> = launchLocalGame(TWO)

    fun runConsoleLocalGame() {
        var game = launchLocalGame()
        do {
            println("player one throw : ${game.firstPlayer()}")
            println("player two throw : ${game.secondPlayer()}")
            val result = game.firstPlayer().compareHands(game.secondPlayer())
            when (result) {
                WIN -> println("player one : $WIN")
                else -> println("player two : $WIN")
            }
        } while (result == RERUN.apply {
                game = launchLocalGame()
            })
    }
}