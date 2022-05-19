package game.ceelo

import game.ceelo.CeeloGameDomain.compareRuns
import game.ceelo.CeeloGameDomain.firstPlayer
import game.ceelo.CeeloGameDomain.runDices
import game.ceelo.CeeloGameDomain.secondPlayer
import game.ceelo.DiceRunResult.*


object CeeloPlaygroundDomain{
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
            val result = game.firstPlayer().compareRuns(
                secondPlayerRun = game.secondPlayer()
            )
            if (result == WIN) println("player one : $WIN")
            else println("player two : $WIN")
        } while (result == RETHROW.apply {
                game = launchLocalGame()
            })
    }
}