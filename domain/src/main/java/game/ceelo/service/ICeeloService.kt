package game.ceelo.service

import game.ceelo.domain.DiceThrowResult.RETHROW
import game.ceelo.domain.DiceThrowResult.WIN
import game.ceelo.domain.compareThrows
import game.ceelo.domain.runDices

interface ICeeloService {
    fun launchLocalGame(): List<List<Int>>
    fun allGames(): List<List<List<Int>>>
    fun saveGame(newGame: List<List<Int>>)
    fun runConsoleLocalGame() = runDices().run playerOne@{
        runDices().run playerTwo@{
            do {
                println("player one throw : ${this@playerOne}")
                println("player two throw : ${this@playerTwo}")
                val result = this@playerOne.compareThrows(
                    secondPlayerThrow = this@playerTwo
                )
                if (result == WIN) println("player one : $WIN")
                else println("player two : $WIN")
            } while (result == RETHROW)
        }
    }
}