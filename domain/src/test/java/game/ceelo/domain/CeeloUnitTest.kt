package game.ceelo.domain

import game.ceelo.domain.DiceThrowResult.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test


@Suppress("NonAsciiCharacters")
class CeeloUnitTest {

    @Test
    fun `Si le jet est correct alors la propriété dicesThrow renvoi un triplet d'entier entre 1 et 6`() =
        dicesThrow.run {
            assertEquals(CEELO_DICE_THROW_SIZE, size)
            forEach { assert(it in ONE..SIX) }
        }

    @Test
    fun `Si le jet contient (4,5,6) alors la propriété is456 renvoi true`() {
        assert(listOf(4, 5, 6).is456)
        assert(listOf(4, 6, 5).is456)
        assert(listOf(5, 4, 6).is456)
        assert(listOf(5, 6, 4).is456)
        assert(listOf(6, 5, 4).is456)
        assert(listOf(6, 4, 5).is456)
    }

    @Test
    fun `Si le jet ne contient pas (4,5,6) alors la propriété is456 renvoi false`() {
        assertFalse(`1_2_3`.is456)
        UNIFORM_TRIPLETS.map { assertFalse(it.is456) }
    }

    @Test
    fun `Si le jet contient (1,2,3) alors la propriété is123 renvoi true`() {
        assert(listOf(1, 2, 3).is123)
        assert(listOf(1, 3, 2).is123)
        assert(listOf(2, 1, 3).is123)
        assert(listOf(2, 3, 1).is123)
        assert(listOf(3, 2, 1).is123)
        assert(listOf(3, 1, 2).is123)
    }

    @Test
    fun `Si le jet ne contient pas (1,2,3) alors la propriété is123 renvoi false`() {
        assertFalse(`1_2_3`.is456)
        UNIFORM_TRIPLETS.map { assertFalse(it.is123) }
    }

    @Test
    fun `Si le jet est un triplet uniforme alors la propriété isUniformTriplet renvoi un true`() {
        assert(`1_1_1`.isUniformTriplet)
        assert(`2_2_2`.isUniformTriplet)
        assert(`3_3_3`.isUniformTriplet)
        assert(`4_4_4`.isUniformTriplet)
        assert(`5_5_5`.isUniformTriplet)
        assert(`6_6_6`.isUniformTriplet)
        assertFalse(`4_5_6`.isUniformTriplet)
        assertFalse(`1_2_3`.isUniformTriplet)
    }

    @Test
    fun `Si le jet est un triplet uniforme alors la propriété uniformTripletValue renvoi la valeur facial du dé`() {
        assertEquals(NOT_A_TRIPLET, `1_2_3`.uniformTripletValue)
        assertEquals(ONE, `1_1_1`.uniformTripletValue)
        assertEquals(TWO, `2_2_2`.uniformTripletValue)
        assertEquals(THREE, `3_3_3`.uniformTripletValue)
        assertEquals(FOUR, `4_4_4`.uniformTripletValue)
        assertEquals(FIVE, `5_5_5`.uniformTripletValue)
        assertEquals(SIX, `6_6_6`.uniformTripletValue)
    }

    @Test
    fun `Si le jet comporte un doublet uniforme alors la propriété isUniformDoublet renvoi un true`() {
        assert(listOf(1, 1, 6).isUniformDoublet)
        assert(listOf(2, 2, 5).isUniformDoublet)
        assert(listOf(3, 3, 4).isUniformDoublet)
        assert(listOf(4, 4, 3).isUniformDoublet)
        assert(listOf(5, 5, 2).isUniformDoublet)
        assert(listOf(6, 6, 1).isUniformDoublet)
        assertFalse(`4_5_6`.isUniformDoublet)
        assertFalse(`1_2_3`.isUniformDoublet)
        UNIFORM_TRIPLETS.map {
            assertFalse(it.isUniformDoublet)
        }
    }

    @Test
    fun `Si le jet comporte un doublet uniforme alors la propriété uniformDoubletValue renvoi la valeur facial du dé non uniforme`() {
        assertEquals(SIX, listOf(1, 1, 6).uniformDoubletValue)
        assertEquals(FIVE, listOf(2, 2, 5).uniformDoubletValue)
        assertEquals(FOUR, listOf(3, 3, 4).uniformDoubletValue)
        assertEquals(THREE, listOf(4, 4, 3).uniformDoubletValue)
        assertEquals(TWO, listOf(5, 5, 2).uniformDoubletValue)
        assertEquals(ONE, listOf(6, 6, 1).uniformDoubletValue)
    }

    @Test
    fun `Si le jet ne comporte pas uniquement un doublet uniforme alors la propriété uniformDoubletValue renvoi la valeur NOT_A_DOUBLET`() {
        assertEquals(NOT_A_DOUBLET, `1_2_3`.uniformDoubletValue)
        assertEquals(NOT_A_DOUBLET, `4_5_6`.uniformDoubletValue)
        UNIFORM_TRIPLETS.map {
            assertEquals(NOT_A_DOUBLET, it.uniformDoubletValue)
        }
    }

    @Test
    fun `Si le jet contient (2,3,4) ou (3,4,5) alors la propriété isStraight renvoi true`() {
        assert(listOf(2, 3, 4).isStraight)
        assert(listOf(3, 4, 5).isStraight)
    }

    @Test
    fun `Si le jet ne contient pas (2,3,4) ou (3,4,5) alors la propriété isStraight renvoi false`() {
        UNIFORM_TRIPLETS.map {
            assertFalse(it.containsAll(STRAIGHT_TRIPLETS.first()))
            assertFalse(it.containsAll(STRAIGHT_TRIPLETS.last()))
        }
        assertFalse(STRAIGHT_TRIPLETS.map { it.containsAll(`1_2_3`) }.contains(true))
        assertFalse(STRAIGHT_TRIPLETS.map { it.containsAll(`4_5_6`) }.contains(true))
    }

    @Test
    fun `Si le jet contient (4,5,6) alors la propriété whichCase renvoi AUTOMATIC_WIN_456_CASE`() {
        assertEquals(AUTOMATIC_WIN_456_CASE, listOf(4, 5, 6).whichCase)
    }

    @Test
    fun `Si le jet contient (1,2,3) alors la propriété whichCase renvoi AUTOMATIC_LOOSE_123_CASE`() {
        assertEquals(AUTOMATIC_LOOSE_123_CASE, listOf(1, 2, 3).whichCase)
    }

    @Test
    fun `Si le jet contient (2,3,4) ou (3,4,5) alors la propriété whichCase renvoi STRAIGHT_234_345_CASE`() {
        assertEquals(STRAIGHT_234_345_CASE, listOf(2, 3, 4).whichCase)
        assertEquals(STRAIGHT_234_345_CASE, listOf(3, 4, 5).whichCase)
    }

    @Test
    fun `Si le jet contient un triplet uniforme alors la propriété whichCase renvoi TRIPLET_CASE`() {
        UNIFORM_TRIPLETS.map {
            assertEquals(TRIPLET_CASE, it.whichCase)
        }
    }

    @Test
    fun `Si le jet contient un doublet uniforme uniquement alors la propriété whichCase renvoi DOUBLET_CASE`() {
        assertEquals(DOUBLET_CASE, listOf(1, 1, 6).whichCase)
        assertEquals(DOUBLET_CASE, listOf(2, 2, 5).whichCase)
        assertEquals(DOUBLET_CASE, listOf(3, 3, 4).whichCase)
        assertEquals(DOUBLET_CASE, listOf(4, 4, 3).whichCase)
        assertEquals(DOUBLET_CASE, listOf(5, 5, 2).whichCase)
        assertEquals(DOUBLET_CASE, listOf(6, 6, 1).whichCase)
    }

    @Test
    fun `Si le jet ne contient ni (4,5,6) ni (1,2,3) ni triplet uniforme ni doublet uniforme alors la propriété whichCase renvoi OTHERS_CASE`() {
        assertEquals(OTHERS_CASE, listOf(1, 3, 6).whichCase)
    }

    @Test
    fun `Si le jet contient (4,5,6) et l'autre (1,2,3) alors la propriété compareThrows renvoi WIN`() =
        assertEquals(WIN, `4_5_6`.compareThrows(`1_2_3`))


    @Test
    fun `Si le jet contient (4,5,6) non ordonné et l'autre (1,2,3) alors la propriété compareThrows renvoi WIN`() =
        assertEquals(listOf(5, 6, 4).compareThrows(`1_2_3`), WIN)

    @Test
    fun `Si le jet contient 4 5 6 et l'autre aussi alors la propriété compareThrows renvoi RETHROW`() =
        assertEquals(`4_5_6`.compareThrows(`4_5_6`), RETHROW)

    @Test
    fun `Si le jet contient (1,2,3) et l'autre (4,5,6) alors la propriété compareThrows renvoi LOOSE`() =
        assertEquals(`1_2_3`.compareThrows(`4_5_6`), LOOSE)
//
//    @Test
//    fun `Si le jet contient 1 2 3 non ordonné et l'autre non alors je perds`() =
//        assertEquals(listOf(3, 2, 1).compareThrows(`4_5_6`), LOOSE)
//
//    @Test
//    fun `Si le jet contient 1 2 3 et l'autre aussi alors rejouer`() =
//        assertEquals(`1_2_3`.compareThrows(`1_2_3`), RETHROW)
//
//    @Test
//    fun `Si le jet est un triplet et l'autre aussi alors le triplet le plus fort gagne`() {
//        assertEquals(`6_6_6`.compareThrows(`5_5_5`), WIN)
//        assertEquals(`6_6_6`.compareThrows(`4_4_4`), WIN)
//        assertEquals(`6_6_6`.compareThrows(`3_3_3`), WIN)
//        assertEquals(`6_6_6`.compareThrows(`2_2_2`), WIN)
//        assertEquals(`6_6_6`.compareThrows(`1_1_1`), WIN)
//    }
//
//    @Test
//    @Ignore
//    fun `Si le jet est un triplet et l'autre aussi alors le triplet le plus faible perd`() {
//        assertEquals(`1_1_1`.compareThrows(`6_6_6`), LOOSE)
//        assertEquals(`1_1_1`.compareThrows(`5_5_5`), LOOSE)
//        assertEquals(`1_1_1`.compareThrows(`4_4_4`), LOOSE)
//        assertEquals(`1_1_1`.compareThrows(`3_3_3`), LOOSE)
//        assertEquals(`1_1_1`.compareThrows(`2_2_2`), LOOSE)
//    }
//
//    @Test
//    @Ignore
//    fun `Si le jet est un triplet alors les triplets égaux font rejouer`() {
//        assertEquals(`6_6_6`.compareThrows(`6_6_6`), RETHROW)
//        assertEquals(`5_5_5`.compareThrows(`5_5_5`), RETHROW)
//        assertEquals(`4_4_4`.compareThrows(`4_4_4`), RETHROW)
//        assertEquals(`3_3_3`.compareThrows(`3_3_3`), RETHROW)
//        assertEquals(`2_2_2`.compareThrows(`2_2_2`), RETHROW)
//        assertEquals(`1_1_1`.compareThrows(`1_1_1`), RETHROW)
//    }
}