package game.ceelo

import game.ceelo.DiceThrowResult.RETHROW
import game.ceelo.DiceThrowResult.WIN
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test

@Suppress("NonAsciiCharacters")
class CeeloUnitTest {

    @Test
    fun `Quand je lance les dés je récupère un triplet d'entier entre 1 et 6`() {
        throwDices().run {
            assertEquals(size, CEELO_DICE_THROW_SIZE)
            forEach { diceValue -> assertTrue(diceValue in ONE..SIX) }
        }
    }

    @Test
    fun `si mon jet contient 4 5 6 et l'autre non alors je gagne `() {
        assertEquals(FOUR_FIVE_SIX.evalThrows(ONE_TWO_THREE), WIN)
    }

    @Test
    fun `si mon jet contient 4 5 6 et l'autre aussi alors rejouer`() {
        assertEquals(FOUR_FIVE_SIX.evalThrows(FOUR_FIVE_SIX), RETHROW)
    }

    @Test
    fun `si mon jet contient 4 5 6 non ordonné et l'autre non alors je gagne `() {
        val unorderedRoyalFlushThrow = listOf(6, 5, 4)
        assertEquals(unorderedRoyalFlushThrow.evalThrows(ONE_TWO_THREE), WIN)
    }
}