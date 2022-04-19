package game.ceelo

import game.ceelo.DiceThrowResult.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test

@Suppress("NonAsciiCharacters")
class CeeloUnitTest {

    @Test
    fun `Quand je lance les dés je récupère un triplet d'entier entre 1 et 6`() =
        dicesThrow.run {
            assertEquals(size, CEELO_DICE_THROW_SIZE)
            forEach { assertTrue(it in ONE..SIX) }
        }

    @Test
    fun `si mon jet contient 4 5 6 et l'autre non alors je gagne`() =
        assertEquals(`4_5_6`.compareThrows(`1_2_3`), WIN)

    @Test
    fun `si mon jet contient 4 5 6 non ordonné et l'autre non alors je gagne`() =
        assertEquals(listOf(5, 6, 4).compareThrows(`1_2_3`), WIN)

    @Test
    fun `si mon jet contient 4 5 6 et l'autre aussi alors rejouer`() =
        assertEquals(`4_5_6`.compareThrows(`4_5_6`), RETHROW)

    @Test
    fun `si mon jet contient 1 2 3 et l'autre non alors je perds`() =
        assertEquals(`1_2_3`.compareThrows(`4_5_6`), LOOSE)

    @Test
    fun `si mon jet contient 1 2 3 non ordonné et l'autre non alors je perds`() =
        assertEquals(listOf(3, 2, 1).compareThrows(`4_5_6`), LOOSE)

    @Test
    fun `si mon jet contient 1 2 3 et l'autre aussi alors rejouer`() =
        assertEquals(`1_2_3`.compareThrows(`1_2_3`), RETHROW)

    @Test
    fun `si le jet est un triplet alors la function isTriplet renvoi un booléen`() {
        assertEquals(true, isTriplet(`1_1_1`))
        assertEquals(true, isTriplet(`2_2_2`))
        assertEquals(true, isTriplet(`3_3_3`))
        assertEquals(true, isTriplet(`4_4_4`))
        assertEquals(true, isTriplet(`5_5_5`))
        assertEquals(true, isTriplet(`6_6_6`))
        assertEquals(false, isTriplet(`4_5_6`))
        assertEquals(false, isTriplet(`1_2_3`))
    }

    @Test
    fun `si mon jet est un triplet alors je peux identifer sa valeur`() {
        assertEquals(NOT_A_TRIPLET, whichTripletIsIt(`1_2_3`))
        assertEquals(ONE, whichTripletIsIt(`1_1_1`))
        assertEquals(TWO, whichTripletIsIt(`2_2_2`))
        assertEquals(THREE, whichTripletIsIt(`3_3_3`))
        assertEquals(FOUR, whichTripletIsIt(`4_4_4`))
        assertEquals(FIVE, whichTripletIsIt(`5_5_5`))
        assertEquals(SIX, whichTripletIsIt(`6_6_6`))
    }

    @Test
    fun `si mon jet est un triplet et l'autre aussi alors le triplet le plus fort gagne`() {
        assertEquals(`6_6_6`.compareThrows(`5_5_5`), WIN)
        assertEquals(`6_6_6`.compareThrows(`4_4_4`), WIN)
        assertEquals(`6_6_6`.compareThrows(`3_3_3`), WIN)
        assertEquals(`6_6_6`.compareThrows(`2_2_2`), WIN)
        assertEquals(`6_6_6`.compareThrows(`1_1_1`), WIN)
    }

    @Test
    @Ignore
    fun `si mon jet est un triplet et l'autre aussi alors le triplet le plus faible perd`() {
        assertEquals(`1_1_1`.compareThrows(`6_6_6`), LOOSE)
        assertEquals(`1_1_1`.compareThrows(`5_5_5`), LOOSE)
        assertEquals(`1_1_1`.compareThrows(`4_4_4`), LOOSE)
        assertEquals(`1_1_1`.compareThrows(`3_3_3`), LOOSE)
        assertEquals(`1_1_1`.compareThrows(`2_2_2`), LOOSE)
    }

    @Test
    @Ignore
    fun `si mon jet est un triplet alors les triplets égaux font rejouer`() {
        assertEquals(`6_6_6`.compareThrows(`6_6_6`), RETHROW)
        assertEquals(`5_5_5`.compareThrows(`5_5_5`), RETHROW)
        assertEquals(`4_4_4`.compareThrows(`4_4_4`), RETHROW)
        assertEquals(`3_3_3`.compareThrows(`3_3_3`), RETHROW)
        assertEquals(`2_2_2`.compareThrows(`2_2_2`), RETHROW)
        assertEquals(`1_1_1`.compareThrows(`1_1_1`), RETHROW)
    }
}