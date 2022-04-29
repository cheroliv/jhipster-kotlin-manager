package game.ceelo.model

data class DiceThrow(
    val diceThrowId: Long,
    val firstDice: Int,
    val middleDice: Int,
    val lastDice: Int
)