package game.ceelo.service

import game.ceelo.domain.DiceThrowResult
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
                if (result == DiceThrowResult.WIN) println("player one : ${DiceThrowResult.WIN}")
                else println("player two : ${DiceThrowResult.WIN}")
            } while (result == DiceThrowResult.RETHROW)
        }
    }
}