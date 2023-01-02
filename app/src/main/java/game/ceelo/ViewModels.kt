@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "unused"
)

package game.ceelo

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import game.ceelo.Constant.ONE
import game.ceelo.Hand.compareHands
import game.ceelo.Game.runDices
import game.ceelo.Game.secondPlayer
import game.ceelo.GameResult.*

class DiceGameViewModel(val gameService: GameService) : ViewModel() {
    private val _resultPair: MutableLiveData<List<Pair<GameResult, Int>>> = MutableLiveData()
    val resultPairList: LiveData<List<Pair<GameResult, Int>>> = _resultPair

    private val _resultVisibility: MutableLiveData<Int> = MutableLiveData()
    val resultVisibility: LiveData<Int> = _resultVisibility

    private val _diceGame: MutableLiveData<List<List<Int>>> =
        MutableLiveData(listOf(listOf(ONE, ONE, ONE), listOf(ONE, ONE, ONE)))
    val diceGame: LiveData<List<List<Int>>> = _diceGame

    private val _games: MutableLiveData<List<List<List<Int>>>> = MutableLiveData()
    val games: LiveData<List<List<List<Int>>>> = _games

    private val _greetingVisibility: MutableLiveData<Int> = MutableLiveData()
    val greetingVisibility: LiveData<Int> = _greetingVisibility

    private val _greeting: MutableLiveData<String> = MutableLiveData()
    val greeting: LiveData<String> = _greeting

    fun onClickPlayButton() {
        //TODO: ici pour utiliser le service room
        _diceGame.value = listOf(runDices(), runDices())

        gameService.saveGame(_diceGame.value!!)

        _resultVisibility.value = VISIBLE
        _games.value = gameService.allGames()

        val resultPlayer = _diceGame.value!!.first()
            .compareHands(_diceGame.value!!.secondPlayer())

        _resultPair.value = listOf(
            Pair(
                resultPlayer, when (resultPlayer) {
                    WIN, RERUN -> VISIBLE
                    else -> GONE
                }
            ),
            Pair(
                when (resultPlayer) {
                    WIN -> LOOSE
                    LOOSE -> WIN
                    else -> RERUN
                }, when (resultPlayer) {
                    LOOSE, RERUN -> VISIBLE
                    else -> GONE
                }
            )
        )
    }

    fun onClickSignInButton() {
        _greetingVisibility.value = VISIBLE
    }

    fun onClickSignOutButton() {
        _greetingVisibility.value = GONE
    }

}