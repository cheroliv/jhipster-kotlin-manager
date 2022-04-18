package game.ceelo

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
    fun `si mon jet contient 4 5 6 alors je gagne`() {
        val royalFlushThrow = listOf(4,5,6)
        val otherThrow = throwDices()
        println(royalFlushThrow)
        println(otherThrow)
        assertTrue(royalFlushThrow.evalThrows(otherThrow)!!)
    }
}

