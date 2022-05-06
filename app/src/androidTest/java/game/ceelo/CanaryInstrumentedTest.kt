package game.ceelo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CanaryInstrumentedTest {
    @Test
    fun canary_test() {
        getInstrumentation().targetContext.apply {
            assertEquals("game.ceelo", packageName)
        }
    }
}