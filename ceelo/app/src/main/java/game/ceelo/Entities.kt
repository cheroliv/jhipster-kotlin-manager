package game.ceelo

import java.util.*

class LocalGame

data class DicesRun(
    val diceThrowId: Long,
    val gameId: Long,
    val playerId: Long,
    val firstDice: Int,
    val middleDice: Int,
    val lastDice: Int
)

data class Game(
    val gameId: Long,
    val winnerPlayerId: Long,
    val date: Date,
    val isDraw: Boolean,
    val isOffLineGame: Boolean,
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

