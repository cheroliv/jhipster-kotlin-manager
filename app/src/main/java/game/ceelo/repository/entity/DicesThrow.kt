package game.ceelo.repository.entity

data class DicesThrow(
    val diceThrowId: Long,
    val gameId:Long,
    val playerId:Long,
    val firstDice: Int,
    val middleDice: Int,
    val lastDice: Int
)