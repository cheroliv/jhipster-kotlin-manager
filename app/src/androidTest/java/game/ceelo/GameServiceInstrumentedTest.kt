@file:Suppress(
    "NonAsciiCharacters",
    "TestFunctionName",
    "SpellCheckingInspection"
)

package game.ceelo

import androidx.test.ext.junit.runners.AndroidJUnit4
import game.ceelo.Constant.CEELO_DICE_THROW_SIZE
import game.ceelo.Constant.ONE
import game.ceelo.Constant.SIX
import game.ceelo.Game.runDices
import game.ceelo.Playground.launchLocalGame
import org.junit.runner.RunWith
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


@RunWith(AndroidJUnit4::class)
class GameServiceInstrumentedTest {
    private val gameService: GameService by lazy { GameServiceInMemory() }

    @BeforeTest
    fun initService() {

    }

    @Test
    fun localDicesThrow_retourne_un_jeux_de_jet_de_dès_correct() {
        launchLocalGame().run {
            assertEquals(2, size)
            first().run hand@{
                assertEquals(CEELO_DICE_THROW_SIZE, this@hand.size)
                forEach { assert(it in ONE..SIX) }
            }
            last().run hand@{
                assertEquals(CEELO_DICE_THROW_SIZE, this@hand.size)
                forEach { assert(it in ONE..SIX) }
            }
        }
    }

    @Test
    fun allGames_retourne_toutes_les_parties_et_sont_correct() {
        gameService
            .allGames()
            .forEach { game ->
                assertEquals(2, game.size)
                game.first().run {
                    assertEquals(CEELO_DICE_THROW_SIZE, size)
                    forEach { assert(it in (ONE..SIX)) }
                }
                game.last().run {
                    assertEquals(CEELO_DICE_THROW_SIZE, size)
                    forEach { assert(it in (ONE..SIX)) }
                }
            }
    }

    @Test
    fun saveGame_ajoute_une_partie() {
        gameService.allGames().size.run {
            gameService.saveGame(listOf(runDices(), runDices()))
            assertEquals(this + 1, gameService.allGames().size)
        }
    }
}