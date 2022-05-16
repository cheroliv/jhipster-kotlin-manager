package game.ceelo.inmemory

import game.ceelo.CeeloDicesHandDomain.runDices
import game.ceelo.CeeloService
import game.ceelo.inmemory.CeeloServiceInMemory.InMemoryData.addGame
import game.ceelo.inmemory.CeeloServiceInMemory.InMemoryData.getAllGames

val ceeloService: CeeloService by lazy {
    CeeloServiceInMemory()
}

class CeeloServiceInMemory : CeeloService {
    object InMemoryData {
        private val repo: MutableList<List<List<Int>>> by lazy {
            MutableList(size = 10, init = { mutableListOf(runDices(), runDices()) })
        }

        @JvmStatic
        fun getAllGames(): List<List<List<Int>>> = repo

        @JvmStatic
        fun addGame(game: List<List<Int>>) {
            repo.add(game)
        }
    }

    override fun allGames(): List<List<List<Int>>> = getAllGames()
    override fun saveGame(newGame: List<List<Int>>) = addGame(newGame)
    override fun connect() {
        TODO("Not yet implemented")
    }
    override fun suscribe() {
        TODO("Not yet implemented")
    }
}