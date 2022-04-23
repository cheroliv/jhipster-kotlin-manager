package game.ceelo.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Ignore
import org.junit.Test


@Suppress("NonAsciiCharacters")
class CeeloUnitTest {

    @Test
    fun `Si le jet est correct alors la propriété dicesThrow renvoi un triplet d'entier entre 1 et 6`() =
        getDicesThrow().run {
            assertEquals(size, CEELO_DICE_THROW_SIZE)
            forEach { assert(it in ONE..SIX) }
        }

    @Test
    fun `Si le jet contient (4,5,6) alors la propriété is456 renvoi vrai`() {
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
    fun `Si le jet contient (1,2,3) alors la propriété is123 renvoi vrai`() {
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
    fun `Si le jet est un triplet uniforme alors la propriété containsUniformTriplet renvoi un true`() {
        assert(`1_1_1`.containsUniformTriplet)
        assert(`2_2_2`.containsUniformTriplet)
        assert(`3_3_3`.containsUniformTriplet)
        assert(`4_4_4`.containsUniformTriplet)
        assert(`5_5_5`.containsUniformTriplet)
        assert(`6_6_6`.containsUniformTriplet)
        assertFalse(`4_5_6`.containsUniformTriplet)
        assertFalse(`1_2_3`.containsUniformTriplet)
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
    fun `Si le jet comporte un doublet uniforme alors la propriété containsUniformDoublet renvoi un true`() {
        assert(listOf(1, 1, 6).containsUniformDoublet)
        assert(listOf(2, 2, 5).containsUniformDoublet)
        assert(listOf(3, 3, 4).containsUniformDoublet)
        assert(listOf(4, 4, 3).containsUniformDoublet)
        assert(listOf(5, 5, 2).containsUniformDoublet)
        assert(listOf(6, 6, 1).containsUniformDoublet)
        assertFalse(`4_5_6`.containsUniformDoublet)
        assertFalse(`1_2_3`.containsUniformDoublet)
        UNIFORM_TRIPLETS.map {
            assertFalse(it.containsUniformDoublet)
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