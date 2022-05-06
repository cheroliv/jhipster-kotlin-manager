package game.ceelo.service.local.inmemory

import android.content.Context
import game.ceelo.domain.runDices
import game.ceelo.service.ICeeloService
import game.ceelo.service.local.inmemory.CeeloServiceInMemory.InMemoryData.addGame
import game.ceelo.service.local.inmemory.CeeloServiceInMemory.InMemoryData.getAllGames

class CeeloServiceInMemory(
    @Suppress("UNUSED_PARAMETER") context: Context?
) : ICeeloService {
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