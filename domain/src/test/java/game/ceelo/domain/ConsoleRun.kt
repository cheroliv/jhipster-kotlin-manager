package game.ceelo.domain

import game.ceelo.service.CeeloServiceInMemory
import game.ceelo.service.ICeeloService
import kotlin.test.BeforeTest
import kotlin.test.Test


@Suppress("NonAsciiCharacters")
class ConsoleRun {
    private lateinit var ceeloService: ICeeloService

    @BeforeTest
    fun initService(){
        ceeloService = CeeloServiceInMemory()
    }

    @Test
    fun run_main_as_test(): Unit = ceeloService.run {
        println("un jet de dés :")
        runConsoleLocalGame()
    }
}