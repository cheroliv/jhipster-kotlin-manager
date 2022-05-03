package game.ceelo.repository

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4


import org.junit.runner.RunWith
import kotlin.test.*



@RunWith(AndroidJUnit4::class)
class CanaryRepositoryInstrumentedTest {
    @Test
    fun canary_repository_test() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("game.ceelo", appContext.packageName)
    }
}