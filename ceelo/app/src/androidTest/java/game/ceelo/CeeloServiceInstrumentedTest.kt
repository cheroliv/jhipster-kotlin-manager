@file:Suppress(
    "NonAsciiCharacters",
    "TestFunctionName", "SpellCheckingInspection"
)

package game.ceelo

import androidx.test.ext.junit.runners.AndroidJUnit4
import game.ceelo.CeeloGameDomain.runDices
import game.ceelo.CeeloPlaygroundDomain.launchLocalGame
import org.junit.runner.RunWith
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


@RunWith(AndroidJUnit4::class)
class CeeloServiceInstrumentedTest {
    private val ceeloService: CeeloService by lazy { CeeloServiceInMemory() }

    @BeforeTest
    fun initService() {

    }

    @Test
    fun localDicesThrow_retourne_un_jeux_de_jet_de_dès_correct() {
        assertEquals(expected = 2, actual = launchLocalGame().size)
        launchLocalGame().first().run {
            assertEquals(CEELO_DICE_THROW_SIZE, size)
            forEach { assert(it in ONE..SIX) }
        }
        launchLocalGame().last().run {
            assertEquals(CEELO_DICE_THROW_SIZE, size)
            forEach { assert(it in ONE..SIX) }
        }
    }

    @Test
    fun allGames_retourne_toutes_les_parties_et_sont_correct() {
        ceeloService
            .allGames()
            .forEach { it ->
                assertEquals(expected = 2, actual = it.size)
                it.first().run {
                    assertEquals(CEELO_DICE_THROW_SIZE, size)
                    forEach { assert(it in (ONE..SIX)) }
                }
                it.last().run {
                    assertEquals(CEELO_DICE_THROW_SIZE, size)
                    forEach { assert(it in (ONE..SIX)) }
                }
            }
    }

    @Test
    fun saveGame_ajoute_une_partie() {
        val beforeSave = ceeloService.allGames().size
        ceeloService.saveGame(listOf(runDices(), runDices()))
        assertEquals(
            expected = beforeSave + 1,
            actual = ceeloService.allGames().size
        )
    }
}