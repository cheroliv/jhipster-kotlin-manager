package game.ceelo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.runner.RunWith
import kotlin.test.Test
import kotlin.test.assertEquals


@RunWith(AndroidJUnit4::class)
class CanaryInstrumentedTest {
    @Test
    fun canary_test() = getInstrumentation()
        .targetContext
        .packageName
        .run { assertEquals("game.ceelo", this) }
}