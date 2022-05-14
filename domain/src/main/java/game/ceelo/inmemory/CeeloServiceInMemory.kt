package game.ceelo.inmemory

import game.ceelo.Ceelo.runDices
import game.ceelo.CeeloService
import game.ceelo.inmemory.CeeloServiceInMemory.InMemoryData.addGame
import game.ceelo.inmemory.CeeloServiceInMemory.InMemoryData.getAllGames

val ceeloService: CeeloService by lazy {
    CeeloServiceInMemory()
}

class CeeloServiceInMemory : CeeloService {
    object InMemoryData {
        private val repo: MutableList<List<List<Int>>> = MutableList(size = 10, init = {
            listOf(runDices(), runDices())
        })

        @JvmStatic
        fun getAllGames(): List<List<List<Int>>> = repo

        @JvmStatic
        fun addGame(game: List<List<Int>>) {
            repo.add(game)
        }
    }

    override fun launchLocalGame(): List<List<Int>> = listOf(runDices(), runDices())
    override fun allGames(): List<List<List<Int>>> = getAllGames()
    override fun saveGame(newGame: List<List<Int>>) = addGame(newGame)
}