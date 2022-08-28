package game.ceelo

data class DiceRun(
    val firstDice: Int,
    val secondDice: Int,
    val thirdDice: Int,
    val player: Player,
)

sealed class DiceRunResult {
    object WIN : DiceRunResult()
    object LOOSE : DiceRunResult()
    object RERUN : DiceRunResult()

    override fun toString() = when (this) {
        WIN -> "WIN"
        LOOSE -> "LOOSE"
        else -> "RERUN"
    }
}

data class Player(val name:String)
class Game(val hands:List<DiceRun>)
class Playground(val games:List<Game>)