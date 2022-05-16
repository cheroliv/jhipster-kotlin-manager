package game.ceelo

import game.ceelo.CeeloGameDomain.compareThrows
import game.ceelo.CeeloGameDomain.runDices
import game.ceelo.DiceRunResult.*


object CeeloPlaygroundDomain{
    fun launchGame(nbPlayer: Int):List<List<Int>> = mutableListOf()
    fun launchLocalGame(): List<List<Int>> = listOf(
        runDices(),
        runDices()
    )
    fun launchLocalGame(nbPlayers:Int): List<List<Int>> = mutableListOf<List<Int>>().apply{
        repeat(nbPlayers) { add(runDices()) }
    }.toList()

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
}