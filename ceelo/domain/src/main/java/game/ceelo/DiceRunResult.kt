package game.ceelo

sealed class DiceRunResult {
    object WIN : DiceRunResult()
    object LOOSE : DiceRunResult()
    object RERUN : DiceRunResult()
}