package game.ceelo

import game.ceelo.CeeloGameDomain.compareThrows


object CeeloPlaygroundDomain{
    fun launchGame(nbPlayer: Int): Game = Game()
    fun launchLocalGame(): List<List<Int>> = listOf(
        CeeloGameDomain.runDices(),
        CeeloGameDomain.runDices()
    )
    fun runConsoleLocalGame() {
        var playerOne: List<Int> = CeeloGameDomain.runDices()
        var playerTwo: List<Int> = CeeloGameDomain.runDices()
        do {
            println("player one throw : $playerOne")
            println("player two throw : $playerTwo")
            val result = playerOne.compareThrows(
                secondPlayerThrow = playerTwo
            )
            if (result == DiceRunResult.WIN) println("player one : ${DiceRunResult.WIN}")
            else println("player two : ${DiceRunResult.WIN}")
        } while (result == DiceRunResult.RETHROW.apply {
                playerOne = CeeloGameDomain.runDices()
                playerTwo = CeeloGameDomain.runDices()
            })
    }


    fun initPlayground(
        @Suppress("UNUSED_PARAMETER") howMuchPlayer: Int
    ): Playground = Playground()
}