package game.ceelo.service

import game.ceelo.domain.runDices
import game.ceelo.service.CeeloServiceInMemory.InMemoryData.addGame
import game.ceelo.service.CeeloServiceInMemory.InMemoryData.getAllGames

class CeeloServiceInMemory : ICeeloService {
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