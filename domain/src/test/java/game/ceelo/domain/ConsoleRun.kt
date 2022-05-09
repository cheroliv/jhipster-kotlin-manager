package game.ceelo.domain

import game.ceelo.service.CeeloServiceInMemory
import game.ceelo.service.ICeeloService
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.dsl.single
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.BeforeTest
import kotlin.test.Test


@Suppress("NonAsciiCharacters")
class ConsoleRun {

    @Test
    fun run_main_as_test(): Unit =
        startKoin {
            modules(modules = module {
                single<ICeeloService> { CeeloServiceInMemory() }
            })
        }.run {
            get<ICeeloService>(ICeeloService::class.java).run {
                println("un jet de dés :")
                runConsoleLocalGame()
            }
        }

}