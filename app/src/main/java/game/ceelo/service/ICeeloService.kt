package game.ceelo.service

interface ICeeloService {
    fun launchLocalGame(): List<List<Int>>
    fun allGames(): List<List<List<Int>>>
    fun saveGame(newGame: List<List<Int>>)
}