package game.ceelo

import kotlin.test.*

class MainActivityTest {

    @BeforeTest
    fun setUp() {
        println("${this::class.java.simpleName}.setUp()")
    }

    @AfterTest
    fun tearDown() {
        println("${this::class.java.simpleName}.tearDown()")
    }

    @kotlin.test.Test
    fun onCreate() {
        println("${this::class.java.simpleName}.onCreate()")
    }
}