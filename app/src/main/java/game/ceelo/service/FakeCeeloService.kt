package game.ceelo.service

import android.content.Context
import game.ceelo.domain.dicesThrow
import game.ceelo.service.FakeCeeloService.Data.addGame
import game.ceelo.service.FakeCeeloService.Data.getAllGames

class FakeCeeloService(
    @Suppress("UNUSED_PARAMETER") context: Context?
) : ICeeloService {
    object Data {
        private val repo: MutableList<List<List<Int>>> = MutableList(size = 10, init = {
            listOf(dicesThrow, dicesThrow)
        })

        @JvmStatic
        fun getAllGames(): List<List<List<Int>>> = repo

        @Suppress("unused")
        @JvmStatic
        fun addGame(game: List<List<Int>>) {
            repo.add(game)
        }
    }

    override fun launchLocalGame(): List<List<Int>> = listOf(dicesThrow, dicesThrow)
    override fun allGames(): List<List<List<Int>>> = getAllGames()
    override fun saveGame(newGame: List<List<Int>>) = addGame(newGame)
}