@file:Suppress("UNUSED_VARIABLE")

package game.ceelo

import android.content.Intent
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import game.ceelo.CeeloDicesHandDomain.getDiceImageFromDiceValue
import game.ceelo.CeeloDicesHandDomain.middleDice
import game.ceelo.CeeloGameDomain.secondPlayer
import game.ceelo.databinding.ActivityMainBinding
import game.ceelo.vm.DiceGameViewModel


val diceImages: List<Int>
    get() = listOf(
        R.drawable.dice_face_one,
        R.drawable.dice_face_two,
        R.drawable.dice_face_three,
        R.drawable.dice_face_four,
        R.drawable.dice_face_five,
        R.drawable.dice_face_six,
    )

fun ActivityMainBinding.loadLocalGame(
    activity: MainActivity,
    playersUI: List<List<ImageView>>,
    resultUI: List<TextView>
): ActivityMainBinding = apply {
    val diceGameViewModel = ViewModelProvider(activity).get(DiceGameViewModel::class.java)
    diceGameViewModel.diceGame.observe(activity) { game ->
        diceImages.run {
            playerOneUI(game, this, playersUI.first())
            playerTwoUI(game, this)
        }
    }
    //TODO refactor pour avoir un field dans le viewmodel nommé textViewResultPair Pair<result,visibility>
    // on evitera le nested observe
    diceGameViewModel.playerOneResult.observe(activity) { result: DiceRunResult ->
        diceGameViewModel.resultVisibility.observe(activity) { visibility: Int ->
            setTextViewResult(resultUI.first(), result, visibility)
        }
    }
    diceGameViewModel.playerTwoResult.observe(activity) { result: DiceRunResult ->
        diceGameViewModel.resultVisibility.observe(activity) { visibility: Int ->
            setTextViewResult(resultUI.last(), result, visibility)
        }
    }

    playLocalButton.setOnClickListener {
        diceGameViewModel.apply {
            onClickPlayButton()
            resultUI.forEachIndexed { i, it ->
                playerThrow(
                    playersUI[i],
                    diceGame.value!![i],
                    this,
                    it,
                    when (i) {
                        0 -> playerOneResult
                        else -> playerTwoResult
                    }
                )
            }
        }
    }

    statsButton.setOnClickListener {
        activity.startActivity(Intent(activity, StatsActivity::class.java))
    }
    signinButton.setOnClickListener {
        activity.startActivity(
            Intent(
                activity,
                LoginActivity::class.java
            )
        )
    }
}

fun ActivityMainBinding.playerThrow(
    playerUI: List<ImageView>,
    list: List<Int>,
    diceGameViewModel: DiceGameViewModel,
    resultUI: TextView,
    playerResult: LiveData<DiceRunResult>
) = playerUI.forEachIndexed { i, view ->
    runDiceAnimation(view, list[i])
}.apply {
    setTextViewResult(
        textViewResult = resultUI,
        diceResult = playerResult.value!!,
        textViewVisibility = diceGameViewModel.resultVisibility.value!!
    )
}


fun ActivityMainBinding.playerOneUI(
    game: List<List<Int>>,
    diceImages: List<Int>,
    playerUI: List<ImageView>
) {
    playerOneFirstDiceImageId.setImageResource(
        diceImages.getDiceImageFromDiceValue(diceValue = game.first().first())
    )
    playerOneMiddleDiceImageId.setImageResource(
        diceImages.getDiceImageFromDiceValue(diceValue = game.first().middleDice())
    )
    playerOneLastDiceImageId.setImageResource(
        diceImages.getDiceImageFromDiceValue(diceValue = game.first().last())
    )
}


fun ActivityMainBinding.playerTwoUI(
    game: List<List<Int>>,
    diceImages: List<Int>
) {
    playerTwoFirstDiceImageId.setImageResource(
        diceImages.getDiceImageFromDiceValue(
            diceValue = game.secondPlayer().first()
        )
    )
    playerTwoMiddleDiceImageId.setImageResource(
        diceImages.getDiceImageFromDiceValue(
            diceValue = game.secondPlayer().middleDice()
        )
    )
    playerTwoLastDiceImageId.setImageResource(
        diceImages.getDiceImageFromDiceValue(
            diceValue = game.secondPlayer().last()
        )
    )
}

fun runDiceAnimation(
    diceImage: ImageView,
    diceValue: Int,
): Unit = diceImage.apply {
    setImageResource(
        diceImages.getDiceImageFromDiceValue(
            diceValue = diceValue
        )
    )
}.run {
    startAnimation(
        RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply { duration = 500 })
}

fun setTextViewResult(
    textViewResult: TextView,
    diceResult: DiceRunResult,
    textViewVisibility: Int
): TextView = textViewResult.apply {
    text = when (diceResult) {
        DiceRunResult.WIN -> DiceRunResult.WIN.toString()
        DiceRunResult.LOOSE -> DiceRunResult.LOOSE.toString()
        else -> DiceRunResult.RERUN.toString()
    }
    visibility = textViewVisibility
}
