package game.ceelo.service

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import game.ceelo.CeeLoApplication
import game.ceelo.domain.CEELO_DICE_THROW_SIZE
import game.ceelo.domain.ONE
import game.ceelo.domain.SIX
import game.ceelo.domain.runDices
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals


@Suppress(
    "NonAsciiCharacters",
    "TestFunctionName"
)
@RunWith(AndroidJUnit4::class)
class CeeloServiceInstrumentedTest {


//    private val ceeloService: ICeeloService by inject(ICeeloService::class.java)

    @BeforeTest
    fun initService() {

    }

    @Test
    fun localDicesThrow_retourne_un_jeux_de_jet_de_dès_correct() {
//        startKoin {
//            androidLogger()
//            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
//            modules(modules = module {
//                single { CeeloServiceInMemory() }
//            })
//        }
        val ceeloService: ICeeloService by lazy { CeeloServiceInMemory() }//by inject(ICeeloService::class.java)
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
        val ceeloService: ICeeloService by lazy { CeeloServiceInMemory() }
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
        val ceeloService: ICeeloService by lazy { CeeloServiceInMemory() }
        val beforeSave = ceeloService.allGames().size
        ceeloService.saveGame(listOf(runDices(), runDices()))
        assertEquals(
            expected = beforeSave + 1,
            actual = ceeloService.allGames().size
        )
    }
}

