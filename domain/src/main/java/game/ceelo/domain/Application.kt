package game.ceelo.domain

import game.ceelo.service.ICeeloService


fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) =
    /*"ici dans ce main c'est le playground pour tester du code"*/
    println("un jet de dés :").also { ceeloService.runConsoleLocalGame() }

val ceeloService: ICeeloService = CeeloServiceMain()

class CeeloServiceMain : ICeeloService {
    override fun launchLocalGame(): List<List<Int>> {
        TODO("Not yet implemented")
    }

    override fun allGames(): List<List<List<Int>>> {
        TODO("Not yet implemented")
    }

    override fun saveGame(newGame: List<List<Int>>) {
        TODO("Not yet implemented")
    }
}
