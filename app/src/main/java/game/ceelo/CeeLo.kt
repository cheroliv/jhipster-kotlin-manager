package game.ceelo

const val ONE = 1
const val TWO = 2
const val THREE = 3
const val FOUR = 4
const val FIVE = 5
const val SIX = 6
const val CEELO_DICE_THROW_SIZE = 3

fun <T> List<T>.middle(): T {
    if (isEmpty())
        throw NoSuchElementException("dice throw is empty.")
    return this[1]
}

fun throwDices(): List<Int> = listOf(
    (ONE..SIX).random(),
    (ONE..SIX).random(),
    (ONE..SIX).random()
)
fun List<Int>.evalThrows(secondPlayerThrow: List<Int>): Boolean? {
    TODO("Not yet implemented")
}