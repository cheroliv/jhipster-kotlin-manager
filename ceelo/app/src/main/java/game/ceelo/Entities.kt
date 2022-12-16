package game.ceelo

import java.util.*
import kotlin.collections.List

class LocalGameEntity

data class DicesRunEntity(
    val diceThrowId: Long,
    val gameId: Long,
    val playerId: Long,
    val firstDice: Int,
    val middleDice: Int,
    val lastDice: Int
)

data class GameEntity(
    val gameId: UUID,
    val winnerPlayerId: UUID,
    val date: Date,
    val isDraw: Boolean,
    val isOffLineGame: Boolean,
    val playersIds: Set<UUID>
)


/*
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "user",
    indices = [
        Index(value = ["login"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Long? = null,
    val login: String,
    val email: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "date_of_birth")
    val dob: Date,
    val password: String,
)
 */


/*
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "user",
    indices = [
        Index(value = ["login"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Long? = null,
    val login: String,
    val email: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "date_of_birth")
    val dob: Date,
    val password: String,
)
 */

