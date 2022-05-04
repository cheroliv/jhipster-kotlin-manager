package game.ceelo.service

import androidx.test.ext.junit.runners.AndroidJUnit4
import game.ceelo.domain.CEELO_DICE_THROW_SIZE
import game.ceelo.domain.ONE
import game.ceelo.domain.SIX
import game.ceelo.domain.dicesThrow
import org.junit.runner.RunWith
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


@Suppress(
    "NonAsciiCharacters",
    "TestFunctionName"
)
@RunWith(AndroidJUnit4::class)
abstract class AbstractCeeloServiceInstrumentedTest {
    private lateinit var ceeloService: ICeeloService

    @BeforeTest
    abstract fun initContext()


    @Test
    fun localDicesThrow_retourne_un_jeux_de_jet_de_dès_correct() {
        assertEquals(expected = 2, actual = ceeloService.launchLocalGame().size)
        ceeloService.launchLocalGame().first().run {
            assertEquals(CEELO_DICE_THROW_SIZE, size)
            forEach { assert(it in ONE..SIX) }
        }
        ceeloService.launchLocalGame().last().run {
            assertEquals(CEELO_DICE_THROW_SIZE, size)
            forEach { assert(it in ONE..SIX) }
        }
    }

    @Test
    fun allGames_retourne_toutes_les_parties_et_sont_correct() =
        ceeloService.allGames().forEach { it ->
            assertEquals(expected = 2, actual = it.size)
            it.first().run {
                assertEquals(CEELO_DICE_THROW_SIZE, size)
                forEach { assert(it in ONE..SIX) }
            }
            it.last().run {
                assertEquals(CEELO_DICE_THROW_SIZE, size)
                forEach { assert(it in ONE..SIX) }
            }
        }

    @Test
    fun saveGame_ajoute_une_partie() {
        val beforeSave = ceeloService.allGames().size
        ceeloService.saveGame(listOf(dicesThrow, dicesThrow))
        assertEquals(
            expected = beforeSave + 1,
            actual = ceeloService.allGames().size
        )
    }
}

