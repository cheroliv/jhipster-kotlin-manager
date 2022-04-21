package game.ceelo

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Ignore
import org.junit.Test

private val List<Int>.is456: Boolean
    get() = containsAll(`4_5_6`)

@Suppress("NonAsciiCharacters")
class CeeloUnitTest {

    @Test
    fun `Si le jet est correct alors on récupère un triplet d'entier entre 1 et 6`() =
        dicesThrow.run {
            assertEquals(size, CEELO_DICE_THROW_SIZE)
            forEach { assert(it in ONE..SIX) }
        }

    @Test
    fun `Si le jet contient (4,5,6) alors la propriété is456 renvoi vrai`() {
        assert(listOf(4, 5, 6).is456)
        assert(listOf(4, 6, 5).is456)
        assert(listOf(5, 4, 6).is456)
        assert(listOf(6, 5, 4).is456)
        assert(listOf(6, 4, 5).is456)
    }

    @Test
    fun `Si le jet ne contient pas (4,5,6) alors la fonction is456 renvoi false`() {
        assertFalse(`1_2_3`.is456)
        UNIFORM_TRIPLETS.map { assertFalse(it.is456) }
    }

    @Test
    @Ignore
    fun `Si le jet est un triplet uniforme alors la propriété isTriplet renvoi un true`() {
        assertEquals(true, `1_1_1`.isTriplet)
        assertEquals(true, `2_2_2`.isTriplet)
        assertEquals(true, `3_3_3`.isTriplet)
        assertEquals(true, `4_4_4`.isTriplet)
        assertEquals(true, `5_5_5`.isTriplet)
        assertEquals(true, `6_6_6`.isTriplet)
        assertEquals(false, `4_5_6`.isTriplet)
        assertEquals(false, `1_2_3`.isTriplet)
    }

    @Test
    @Ignore
    fun `Si le jet est un triplet uniforme alors la propriete tripletUniformeValue renvoi la valeur facial du dé`() {
        assertEquals(NOT_A_TRIPLET, `1_2_3`.whichTripletIsIt)
        assertEquals(ONE, `1_1_1`.whichTripletIsIt)
        assertEquals(TWO, `2_2_2`.whichTripletIsIt)
        assertEquals(THREE, `3_3_3`.whichTripletIsIt)
        assertEquals(FOUR, `4_4_4`.whichTripletIsIt)
        assertEquals(FIVE, `5_5_5`.whichTripletIsIt)
        assertEquals(SIX, `6_6_6`.whichTripletIsIt)
    }

//    @Test
//    fun `si mon jet contient 4 5 6 et l'autre non alors je gagne`() =
//        assertEquals(`4_5_6`.compareThrows(`1_2_3`), WIN)
//
//    @Test
//    fun `si mon jet contient 4 5 6 non ordonné et l'autre non alors je gagne`() =
//        assertEquals(listOf(5, 6, 4).compareThrows(`1_2_3`), WIN)
//
//    @Test
//    fun `si mon jet contient 4 5 6 et l'autre aussi alors rejouer`() =
//        assertEquals(`4_5_6`.compareThrows(`4_5_6`), RETHROW)
//
//    @Test
//    fun `si mon jet contient 1 2 3 et l'autre non alors je perds`() =
//        assertEquals(`1_2_3`.compareThrows(`4_5_6`), LOOSE)
//
//    @Test
//    fun `si mon jet contient 1 2 3 non ordonné et l'autre non alors je perds`() =
//        assertEquals(listOf(3, 2, 1).compareThrows(`4_5_6`), LOOSE)
//
//    @Test
//    fun `si mon jet contient 1 2 3 et l'autre aussi alors rejouer`() =
//        assertEquals(`1_2_3`.compareThrows(`1_2_3`), RETHROW)
//
//    @Test
//    fun `si mon jet est un triplet et l'autre aussi alors le triplet le plus fort gagne`() {
//        assertEquals(`6_6_6`.compareThrows(`5_5_5`), WIN)
//        assertEquals(`6_6_6`.compareThrows(`4_4_4`), WIN)
//        assertEquals(`6_6_6`.compareThrows(`3_3_3`), WIN)
//        assertEquals(`6_6_6`.compareThrows(`2_2_2`), WIN)
//        assertEquals(`6_6_6`.compareThrows(`1_1_1`), WIN)
//    }
//
//    @Test
//    @Ignore
//    fun `si mon jet est un triplet et l'autre aussi alors le triplet le plus faible perd`() {
//        assertEquals(`1_1_1`.compareThrows(`6_6_6`), LOOSE)
//        assertEquals(`1_1_1`.compareThrows(`5_5_5`), LOOSE)
//        assertEquals(`1_1_1`.compareThrows(`4_4_4`), LOOSE)
//        assertEquals(`1_1_1`.compareThrows(`3_3_3`), LOOSE)
//        assertEquals(`1_1_1`.compareThrows(`2_2_2`), LOOSE)
//    }
//
//    @Test
//    @Ignore
//    fun `si mon jet est un triplet alors les triplets égaux font rejouer`() {
//        assertEquals(`6_6_6`.compareThrows(`6_6_6`), RETHROW)
//        assertEquals(`5_5_5`.compareThrows(`5_5_5`), RETHROW)
//        assertEquals(`4_4_4`.compareThrows(`4_4_4`), RETHROW)
//        assertEquals(`3_3_3`.compareThrows(`3_3_3`), RETHROW)
//        assertEquals(`2_2_2`.compareThrows(`2_2_2`), RETHROW)
//        assertEquals(`1_1_1`.compareThrows(`1_1_1`), RETHROW)
//    }
}