@file:Suppress("MemberVisibilityCanBePrivate")

package game.ceelo

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import game.ceelo.CeeloConstant.ONE
import game.ceelo.CeeloHand.compareHands
import game.ceelo.CeeloGame.runDices
import game.ceelo.CeeloGame.secondPlayer
import game.ceelo.CeeloResult.*

class DiceGameViewModel(val ceeloService: CeeloService) : ViewModel() {
    private val _resultPair: MutableLiveData<List<Pair<CeeloResult, Int>>> = MutableLiveData()
    val resultPairList: LiveData<List<Pair<CeeloResult, Int>>> = _resultPair

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

        ceeloService.saveGame(_diceGame.value!!)

        _resultVisibility.value = VISIBLE
        _games.value = ceeloService.allGames()

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