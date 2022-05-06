package game.ceelo.service.local.inmemory

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import game.ceelo.domain.CEELO_DICE_THROW_SIZE
import game.ceelo.domain.ONE
import game.ceelo.domain.SIX
import game.ceelo.domain.dicesThrow
import game.ceelo.service.AbstractCeeloServiceInstrumentedTest
import game.ceelo.service.ICeeloService
import org.junit.runner.RunWith
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("NonAsciiCharacters", "TestFunctionName")
@RunWith(AndroidJUnit4::class)
class CeeloServiceInMemoryInstrumentedTest : AbstractCeeloServiceInstrumentedTest() {
    private lateinit var ceeloService: ICeeloService

    @BeforeTest
    override fun initContext() {
        ceeloService = CeeloServiceInMemory(getInstrumentation().targetContext)
    }
}