package game.ceelo

import game.ceelo.AndroidCeeloService.InMemoryData.addGame
import game.ceelo.AndroidCeeloService.InMemoryData.getAllGames

//TODO: add room, retrofit
class AndroidCeeloService : CeeloService {
    object InMemoryData {
        private val repo: MutableList<List<List<Int>>> by lazy {
            MutableList(size = 0, init = { mutableListOf(
                CeeloGame.runDices(),
                CeeloGame.runDices()
            ) })
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

    override fun subscribe() {
        TODO("Not yet implemented")
    }
}