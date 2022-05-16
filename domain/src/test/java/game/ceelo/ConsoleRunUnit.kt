package game.ceelo

import game.ceelo.CeeloPlaygroundDomain.runConsoleLocalGame
import game.ceelo.inmemory.CeeloServiceInMemory
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get
import kotlin.test.Test


@Suppress("NonAsciiCharacters")
class ConsoleRunUnit {

    @Test
    fun `run main as test`(): Unit =
        startKoin {
            modules(modules = module {
                single<CeeloService> { CeeloServiceInMemory() }
            })
        }.run {
            get<CeeloService>(CeeloService::class.java).run {
                println("un jet de dés :")
                runConsoleLocalGame()
            }
        }
}