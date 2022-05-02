package game.ceelo.repository.entity

import java.util.*

data class Game(
    val gameId: Long,
    val winnerPlayerId:Long,
    val date: Date,
    val isDraw:Boolean,
    val isOffLineGame:Boolean,
)