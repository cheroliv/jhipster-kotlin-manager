package game.ceelo.vm

import android.app.Application
import android.util.Log
import android.view.View.VISIBLE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import game.ceelo.domain.*
import game.ceelo.service.ICeeloService
import game.ceelo.service.local.inmemory.CeeloServiceInMemory


//TODO refactor pour avoir un field dans le viewmodel nommé textViewResultPair Pair<result,visibility>
// on evitera le nested observe
//class DiceGameViewModel(application: Application) : AndroidViewModel(application) {
    class DiceGameViewModel :ViewModel() {
    private val ceeloService: ICeeloService = CeeloServiceInMemory(null)
    private val _playerOneResult: MutableLiveData<DiceThrowResult> = MutableLiveData()
    val playerOneResult: LiveData<DiceThrowResult> = _playerOneResult
    private val _playerTwoResult: MutableLiveData<DiceThrowResult> = MutableLiveData()
    val playerTwoResult: LiveData<DiceThrowResult> = _playerTwoResult
    private val _resultVisibility: MutableLiveData<Int> = MutableLiveData()
    val resultVisibility: LiveData<Int> = _resultVisibility
    private val _diceGame: MutableLiveData<List<List<Int>>> = MutableLiveData(
        listOf(
            listOf(ONE, ONE, ONE),
            listOf(ONE, ONE, ONE),
        )
    )
    val diceGame: LiveData<List<List<Int>>> = _diceGame
    private val _games: MutableLiveData<List<List<List<Int>>>> = MutableLiveData()
    val games: LiveData<List<List<List<Int>>>> = _games

    fun onClickPlayButton() {
        _diceGame.value = listOf(dicesThrow, dicesThrow)
        ceeloService.saveGame(_diceGame.value!!)
        _playerOneResult.value = _diceGame.value!!.first()
            .compareThrows(_diceGame.value!!.second())
        _playerTwoResult.value = _diceGame.value!!.second()
            .compareThrows(_diceGame.value!!.first())
        _resultVisibility.value = VISIBLE
        _games.value = ceeloService.allGames()
        Log.d("foofoo",_games.value.toString())
    }
}