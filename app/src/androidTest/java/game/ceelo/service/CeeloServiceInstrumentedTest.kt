package game.ceelo.service

import androidx.test.ext.junit.runners.AndroidJUnit4
import game.ceelo.Ceelo.CEELO_DICE_THROW_SIZE
import game.ceelo.Ceelo.ONE
import game.ceelo.Ceelo.SIX
import game.ceelo.Ceelo.runDices
import game.ceelo.CeeloService
import game.ceelo.inmemory.CeeloServiceInMemory
import game.ceelo.inmemory.ceeloService
import org.junit.runner.RunWith
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


@Suppress(
    "NonAsciiCharacters",
    "TestFunctionName"
)
@RunWith(AndroidJUnit4::class)
class CeeloServiceInstrumentedTest {


//    private val CEELO_SERVICE: CeeloService by inject(CeeloService::class.java)

    @BeforeTest
    fun initService() {

    }

    @Test
    fun localDicesThrow_retourne_un_jeux_de_jet_de_dès_correct() {
//        startKoin {
//            androidLogger()
//            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
//            modules(modules = ceeloModule)
//        }
//        val CEELO_SERVICE: CeeloService by lazy { CeeloServiceInMemory() }//by inject(CeeloService::class.java)
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
    fun allGames_retourne_toutes_les_parties_et_sont_correct() {
        val ceeloService: CeeloService by lazy { CeeloServiceInMemory() }
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
    }

    @Test
    fun saveGame_ajoute_une_partie() {
        val ceeloService: CeeloService by lazy { CeeloServiceInMemory() }
        val beforeSave = ceeloService.allGames().size
        ceeloService.saveGame(listOf(runDices(), runDices()))
        assertEquals(
            expected = beforeSave + 1,
            actual = ceeloService.allGames().size
        )
    }
}