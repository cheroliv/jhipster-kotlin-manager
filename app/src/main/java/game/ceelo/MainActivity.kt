package game.ceelo

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import game.ceelo.databinding.ActivityMainBinding
import game.ceelo.databinding.ActivityMainBinding.inflate
import game.ceelo.domain.DiceThrowResult
import game.ceelo.domain.DiceThrowResult.*
import game.ceelo.domain.middle
import game.ceelo.domain.second
import game.ceelo.vm.DiceGameViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate(layoutInflater).apply {
            setContentView(root)
            loadLocalGame(
                binding = this,
                mainActivity = this@MainActivity
            )
            statsButton.setOnClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        StatsActivity::class.java
                    )
                )
            }
            signinButton.setOnClickListener {
                startActivity(Intent(
                    this@MainActivity,
                    LoginActivity::class.java
                ))
            }
        }
    }
}

fun loadLocalGame(
    binding: ActivityMainBinding,
    mainActivity: MainActivity
) = binding.apply {
    val diceGameViewModel = ViewModelProvider(mainActivity)
        .get(DiceGameViewModel::class.java)
    diceGameViewModel.diceGame.observe(mainActivity) { game ->
        diceImages.run {
            playerOneUI(
                activityMainBinding = this@apply,
                game = game,
                list = this
            )
            playerTwoUI(
                activityMainBinding = this@apply,
                game = game,
                list = this
            )
        }

    }
    //TODO refactor pour avoir un field dans le viewmodel nommé textViewResultPair Pair<result,visibility>
    // on evitera le nested observe
    diceGameViewModel.playerOneResult.observe(mainActivity) { result: DiceThrowResult ->
        diceGameViewModel.resultVisibility.observe(mainActivity) { visibility: Int ->
            setTextViewResult(
                textViewResult = localPlayerResultText,
                diceResult = result,
                textViewVisibility = visibility
            )
        }
    }
    diceGameViewModel.playerTwoResult.observe(mainActivity) { result: DiceThrowResult ->
        diceGameViewModel.resultVisibility.observe(mainActivity) { visibility: Int ->
            setTextViewResult(
                textViewResult = computerResultText,
                diceResult = result,
                textViewVisibility = visibility
            )
        }
    }

    playLocalButton.setOnClickListener {
        diceGameViewModel.apply vm@{
            onClickPlayButton()
            diceGame.value.apply {
                this!!.first().apply player@{
                    this@apply!!.second().apply computer@{
                        playerOneThrow(
                            activityMainBinding = binding,
                            list = this@player,
                            diceGameViewModel = this@vm
                        )
                        playerTwoThrow(
                            activityMainBinding = binding,
                            list = this@computer,
                            diceGameViewModel = this@vm
                        )
                    }
                }

            }
        }
    }
}


fun playerOneThrow(
    activityMainBinding: ActivityMainBinding,
    list: List<Int>,
    diceGameViewModel: DiceGameViewModel
) {
    throwDiceAnimation(
        diceImage = activityMainBinding.playerOneFirstDiceImageId,
        diceValue = list.first()
    )
    throwDiceAnimation(
        diceImage = activityMainBinding.playerOneMiddleDiceImageId,
        diceValue = list.middle()
    )
    throwDiceAnimation(
        diceImage = activityMainBinding.playerOneLastDiceImageId,
        diceValue = list.last()
    )
    setTextViewResult(
        textViewResult = activityMainBinding.localPlayerResultText,
        diceResult = diceGameViewModel.playerOneResult.value!!,
        textViewVisibility = diceGameViewModel.resultVisibility.value!!
    )
}

fun playerTwoThrow(
    activityMainBinding: ActivityMainBinding,
    list: List<Int>,
    diceGameViewModel: DiceGameViewModel
) {
    throwDiceAnimation(
        diceImage = activityMainBinding.playerTwoFirstDiceImageId,
        diceValue = list.first()
    )
    throwDiceAnimation(
        diceImage = activityMainBinding.playerTwoMiddleDiceImageId,
        diceValue = list.middle()
    )
    throwDiceAnimation(
        diceImage = activityMainBinding.playerTwoLastDiceImageId,
        diceValue = list.last()
    )
    setTextViewResult(
        textViewResult = activityMainBinding.computerResultText,
        diceResult = diceGameViewModel.playerTwoResult.value!!,
        textViewVisibility = diceGameViewModel.resultVisibility.value!!
    )
}

fun playerOneUI(
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


fun playerTwoUI(
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

fun throwDiceAnimation(
    diceImage: ImageView,
    diceValue: Int
) = diceImage.apply {
    setImageResource(
        getDiceImageFromDiceValue(
            diceValue = diceValue,
            diceImages = diceImages
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

fun setTextViewResult(
    textViewResult: TextView,
    diceResult: DiceThrowResult,
    textViewVisibility: Int
): TextView = textViewResult.apply {
    text = when (diceResult) {
        WIN -> WIN.toString()
        LOOSE -> LOOSE.toString()
        else -> RETHROW.toString()
    }
    visibility = textViewVisibility
}