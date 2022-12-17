package game.ceelo

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "DicesRun", indices = [
        Index(value = ["firstDice"]),
        Index(value = ["middleDice"]),
        Index(value = ["lastDice"])
    ]
)
data class DicesRunEntity(
    @PrimaryKey
    val diceThrowId: UUID,
    val gameId: UUID,
    val playerId: UUID,
    val firstDice: Int,
    val middleDice: Int,
    val lastDice: Int
)

@Entity(
    tableName = "Game", indices = [
        Index("isDraw"),
        Index("isOffLineGame"),
    ]
)
data class GameEntity(
    @PrimaryKey
    val id: UUID,
    val winnerPlayerId: UUID,
    val date: Date,
    val isDraw: Boolean,
    val isOffLineGame: Boolean,
)

@Entity(
    tableName = "Player", indices = [
        Index(value = ["login"], unique = true),
    ]
)
data class PlayerEntity(
    @PrimaryKey
    val id: UUID,
    val login: String,
)