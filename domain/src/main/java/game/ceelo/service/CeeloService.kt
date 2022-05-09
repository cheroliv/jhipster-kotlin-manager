package game.ceelo.service

interface CeeloService {
    fun launchLocalGame(): List<List<Int>>
    fun allGames(): List<List<List<Int>>>
    fun saveGame(newGame: List<List<Int>>)
}