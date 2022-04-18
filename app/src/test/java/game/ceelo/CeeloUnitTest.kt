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
    fun `Quand je lance les dés je récupère un triplet d'entier entre 1 et 6`() =
        throwDices().run {
            assertEquals(size, CEELO_DICE_THROW_SIZE)
            forEach { assertTrue(it in ONE..SIX) }
        }

    @Test
    fun `si mon jet contient 4 5 6 et l'autre non alors je gagne`() =
        assertEquals(`4_5_6`.evalThrows(`1_2_3`), WIN)

    @Test
    fun `si mon jet contient 4 5 6 non ordonné et l'autre non alors je gagne`() =
        assertEquals(listOf(5, 6, 4).evalThrows(`1_2_3`), WIN)

    @Test
    fun `si mon jet contient 4 5 6 et l'autre aussi alors rejouer`() =
        assertEquals(`4_5_6`.evalThrows(`4_5_6`), RETHROW)

    @Test @Ignore
    fun `si mon jet contient 1 2 3 et l'autre non alors je perds`() =
        assertEquals(`1_2_3`.evalThrows(`4_5_6`), WIN)

    @Test @Ignore
    fun `si mon jet contient 1 2 3 non ordonné et l'autre non alors je perds`() =
        assertEquals(listOf(3, 2, 1).evalThrows(`4_5_6`), WIN)

    @Test @Ignore
    fun `si mon jet contient 1 2 3 et l'autre aussi alors rejouer`() =
        assertEquals(`1_2_3`.evalThrows(`1_2_3`), RETHROW)
}