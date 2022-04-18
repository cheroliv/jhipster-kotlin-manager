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
        val royalFlushThrow = listOf(4, 5, 6)
        val otherThrow = listOf(ONE, TWO, THREE)
        assertEquals(royalFlushThrow.evalThrows(otherThrow), WIN)
    }

    @Test
    fun `si mon jet contient 4 5 6 et l'autre aussi alors rejouer`() {
        val royalFlushThrow = listOf(4, 5, 6)
        assertEquals(royalFlushThrow.evalThrows(royalFlushThrow), RETHROW)
    }
}