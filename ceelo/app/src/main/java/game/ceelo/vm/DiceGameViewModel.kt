package game.ceelo.vm

import android.app.Application
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import game.ceelo.CeeloGameDomain.compareRuns
import game.ceelo.CeeloGameDomain.runDices
import game.ceelo.CeeloGameDomain.secondPlayer
import game.ceelo.DiceRunResult
import game.ceelo.ONE
import game.ceelo.inmemory.ceeloService


//TODO refactor pour avoir un field dans le viewmodel nommé textViewResultPair Pair<result,visibility>
// on evitera le nested observe
class DiceGameViewModel(application: Application) : AndroidViewModel(application) {
    //class DiceGameViewModel : ViewModel() {
    private val _playerOneResult: MutableLiveData<DiceRunResult> = MutableLiveData()
    val playerOneResult: LiveData<DiceRunResult> = _playerOneResult
    private val _playerTwoResult: MutableLiveData<DiceRunResult> = MutableLiveData()
    val playerTwoResult: LiveData<DiceRunResult> = _playerTwoResult
    private val _resultVisibility: MutableLiveData<Int> = MutableLiveData()
    val resultVisibility: LiveData<Int> = _resultVisibility
    private val _diceGame: MutableLiveData<List<List<Int>>> =
        MutableLiveData(onStartupDicePosition())
    val diceGame: LiveData<List<List<Int>>> = _diceGame
    private val _games: MutableLiveData<List<List<List<Int>>>> = MutableLiveData()
    val games: LiveData<List<List<List<Int>>>> = _games
    private val _greetingVisibility: MutableLiveData<Int> = MutableLiveData()
    val greetingVisibility: LiveData<Int> = _greetingVisibility
    private val _greeting: MutableLiveData<String> = MutableLiveData()
    val greeting: LiveData<String> = _greeting

    private fun onStartupDicePosition() = listOf(
        listOf(ONE, ONE, ONE),
        listOf(ONE, ONE, ONE),
    )


    fun onClickPlayButton() {
        _diceGame.value = listOf(runDices(), runDices())
        ceeloService.saveGame(_diceGame.value!!)
        _playerOneResult.value = _diceGame.value!!.first()
            .compareRuns(_diceGame.value!!.secondPlayer())
        _playerTwoResult.value = _diceGame.value!!.secondPlayer()
            .compareRuns(_diceGame.value!!.first())
        _resultVisibility.value = VISIBLE
        _games.value = ceeloService.allGames()
        Log.d("foofoo", _games.value.toString())
    }

    fun onClickSignInButton() {
        _greetingVisibility.value = VISIBLE
    }

    fun onClickSignOutButton() {
        _greetingVisibility.value = GONE
    }

}