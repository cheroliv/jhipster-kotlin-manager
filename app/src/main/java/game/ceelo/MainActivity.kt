package game.ceelo

import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import game.ceelo.R.drawable.*
import game.ceelo.databinding.ActivityMainBinding
import game.ceelo.databinding.ActivityMainBinding.inflate
import game.ceelo.domain.*
import game.ceelo.domain.DiceThrowResult.*

data class DiceThrow(
    val diceThrowId: Long,
    val firstDice: Int,
    val middleDice: Int,
    val lastDice: Int
)

data class Game(
    val gameId: Long
)

data class Player(
    val playerId: Long
)

data class Playground(
    val playgroundId: Long
)


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate(layoutInflater).apply {
            setContentView(root)
            loadLocalGame(
                binding = this,
                this@MainActivity
            )
        }
    }
}

class DiceGameViewModel : ViewModel() {
    private val _diceGame: MutableLiveData<List<List<Int>>> = MutableLiveData(
        listOf(
            listOf(ONE, ONE, ONE),
            listOf(ONE, ONE, ONE),
        )
    )
    val diceGame: LiveData<List<List<Int>>> = _diceGame
    fun onClickPlayButton() {
        _diceGame.value = listOf(dicesThrow, dicesThrow)
    }
}

fun loadLocalGame(
    binding: ActivityMainBinding,
    mainActivity: MainActivity
) = binding.apply {
    val diceGameViewModel = ViewModelProvider(mainActivity)
        .get(DiceGameViewModel::class.java)
    diceGameViewModel.diceGame.observe(
        mainActivity
    ) { game ->
        diceImages.run {
            playerOneUI(activityMainBinding = this@apply, game, this)
            playerTwoUI(activityMainBinding = this@apply, game, this)
        }

    }

    playLocalButton.setOnClickListener {
        diceGameViewModel.onClickPlayButton()
        diceGameViewModel.diceGame.value.apply {
            this!!.first().apply player@{
                this@apply!!.second().apply computer@{

                    throwDiceAnimation(playerOneFirstDiceImageId, this@player.first())
                    throwDiceAnimation(playerOneMiddleDiceImageId, this@player.middle())
                    throwDiceAnimation(playerOneLastDiceImageId, this@player.last())

                    throwDiceAnimation(playerTwoFirstDiceImageId, this@computer.first())
                    throwDiceAnimation(playerTwoMiddleDiceImageId, this@computer.middle())
                    throwDiceAnimation(playerTwoLastDiceImageId, this@computer.last())

                    setTextViewResult(
                        localPlayerResultText,
                        this@player.compareThrows(secondPlayerThrow = this@computer)
                    )
                    setTextViewResult(
                        computerResultText,
                        this@computer.compareThrows(secondPlayerThrow = this@player)
                    )
                }
            }
        }

    }
}

private fun playerTwoUI(
    activityMainBinding: ActivityMainBinding,
    game: List<List<Int>>,
    list: List<Int>
) {
    activityMainBinding.playerTwoFirstDiceImageId.setImageResource(
        getDiceImageFromDiceValue(
            diceValue = game.second().first(),
            diceImages = list
        )

    )
    activityMainBinding.playerTwoMiddleDiceImageId.setImageResource(
        getDiceImageFromDiceValue(
            diceValue = game.second().middle(),
            diceImages = list
        )
    )
    activityMainBinding.playerTwoLastDiceImageId.setImageResource(
        getDiceImageFromDiceValue(
            diceValue = game.second().last(),
            diceImages = list
        )
    )
}

private fun playerOneUI(
    activityMainBinding: ActivityMainBinding,
    game: List<List<Int>>,
    list: List<Int>
) {
    activityMainBinding.playerOneFirstDiceImageId.setImageResource(
        getDiceImageFromDiceValue(
            diceValue = game.first().first(),
            diceImages = list
        )
    )
    activityMainBinding.playerOneMiddleDiceImageId.setImageResource(
        getDiceImageFromDiceValue(
            diceValue = game.first().middle(),
            diceImages = list
        )
    )
    activityMainBinding.playerOneLastDiceImageId.setImageResource(
        getDiceImageFromDiceValue(
            diceValue = game.first().last(),
            diceImages = list
        )
    )
}

val diceImages: List<Int> by lazy {
    listOf(
        dice_face_one,
        dice_face_two,
        dice_face_three,
        dice_face_four,
        dice_face_five,
        dice_face_six,
    )
}


fun throwDiceAnimation(
    diceImage: ImageView,
    diceValue: Int
) = diceImage.apply {
    setImageResource(
        getDiceImageFromDiceValue(
            diceValue,
            diceImages
        )
    )
}.run {
    startAnimation(RotateAnimation(
        0f,
        360f,
        RELATIVE_TO_SELF,
        0.5f,
        RELATIVE_TO_SELF,
        0.5f
    ).apply { duration = 500 })
}

fun getDiceImageFromDiceValue(
    diceValue: Int,
    diceImages: List<Int>
): Int = when (diceValue) {
    ONE -> diceImages[ONE - 1]
    TWO -> diceImages[TWO - 1]
    THREE -> diceImages[THREE - 1]
    FOUR -> diceImages[FOUR - 1]
    FIVE -> diceImages[FIVE - 1]
    SIX -> diceImages[SIX - 1]
    else -> throw Exception("Only six faces is possible!")
}

fun setTextViewResult(
    textViewResult: TextView,
    diceResult: DiceThrowResult
): TextView = textViewResult.apply {
    text = when (diceResult) {
        WIN -> WIN.toString()
        LOOSE -> LOOSE.toString()
        else -> RETHROW.toString()
    }
    visibility = VISIBLE
}



