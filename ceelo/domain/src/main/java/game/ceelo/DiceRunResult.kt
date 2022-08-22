package game.ceelo

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