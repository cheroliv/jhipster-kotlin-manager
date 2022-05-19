package game.ceelo.repository


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.runner.RunWith
import kotlin.test.Test
import kotlin.test.assertEquals


@RunWith(AndroidJUnit4::class)
class CanaryRepositoryInstrumentedTest {
    @Test
    fun canary_repository_test() {
        // Context of the app under test.
        val appContext = getInstrumentation().targetContext
        assertEquals("game.ceelo", appContext.packageName)
    }
}