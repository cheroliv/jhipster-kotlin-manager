package game.ceelo

import game.ceelo.domain.*

fun getDiceImageFromDiceValue(
    diceValue: Int,
    diceImages: List<Int>
): Int = when (diceValue) {
    ONE -> diceImages[ONE - 1]
    TWO -> diceImages[TWO - 1]
    THREE -> diceImages[THREE - 1]
    FOUR -> diceImages[FOUR - 1]
    FIVE -> diceImages[FIVE - 1]
    SIX -> diceImages[SIX - 1]
    else -> throw Exception("Only six faces is possible!")
}