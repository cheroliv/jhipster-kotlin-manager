package game.ceelo

import game.ceelo.Constant.TWO
import game.ceelo.Hand.compareHands
import game.ceelo.Game.firstPlayer
import game.ceelo.Game.runDices
import game.ceelo.Game.secondPlayer
import game.ceelo.GameResult.RERUN
import game.ceelo.GameResult.WIN


object Playground {
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