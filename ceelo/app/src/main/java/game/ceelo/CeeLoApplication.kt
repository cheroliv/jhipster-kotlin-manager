package game.ceelo

import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import game.ceelo.CeeloDicesHandDomain.getDiceImageFromDiceValue
import game.ceelo.CeeloDicesHandDomain.middleDice
import game.ceelo.CeeloGameDomain.secondPlayer
import game.ceelo.R.drawable.*
import game.ceelo.databinding.ActivityMainBinding
import game.ceelo.inmemory.CeeloServiceInMemory
import game.ceelo.vm.DiceGameViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module


class CeeLoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        debug(msg = "$this.onCreate()")
        startKoin {
            androidLogger()
            androidContext(this@CeeLoApplication)
            modules(modules = ceeloModule)
        }
    }
}

private fun CeeLoApplication.debug(msg: String) = this::class.java.name.run {
    Log.d(this, msg)
}

@JvmField
val ceeloModule = module {
    single<CeeloService> { CeeloServiceInMemory() }
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

fun loadLocalGame(
    binding: ActivityMainBinding,
    activity: MainActivity
): ActivityMainBinding = binding.apply {
    val diceGameViewModel = ViewModelProvider(activity)
        .get(DiceGameViewModel::class.java)
    diceGameViewModel.diceGame.observe(activity) { game ->
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
    diceGameViewModel.playerOneResult.observe(activity) { result: DiceRunResult ->
        diceGameViewModel.resultVisibility.observe(activity) { visibility: Int ->
            setTextViewResult(
                textViewResult = localPlayerResultText,
                diceResult = result,
                textViewVisibility = visibility
            )
        }
    }
    diceGameViewModel.playerTwoResult.observe(activity) { result: DiceRunResult ->
        diceGameViewModel.resultVisibility.observe(activity) { visibility: Int ->
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
            val game = diceGame.value.apply game@{
                val player: List<Int> = this@game!!.first().apply player@{
                    val computer: List<Int> = this@game.secondPlayer().apply computer@{
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
    statsButton.setOnClickListener {
        activity.startActivity(
            Intent(
                activity,
                StatsActivity::class.java
            )
        )
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
        diceValue = list.middleDice()
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
        diceValue = list.middleDice()
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
        list.getDiceImageFromDiceValue(diceValue = game.first().first())
    )
    activityMainBinding.playerOneMiddleDiceImageId.setImageResource(
        list.getDiceImageFromDiceValue(diceValue = game.first().middleDice())
    )
    activityMainBinding.playerOneLastDiceImageId.setImageResource(
        list.getDiceImageFromDiceValue(diceValue = game.first().last())
    )
}


fun playerTwoUI(
    activityMainBinding: ActivityMainBinding,
    game: List<List<Int>>,
    list: List<Int>
) {
    activityMainBinding.playerTwoFirstDiceImageId.setImageResource(
        list.getDiceImageFromDiceValue(
            diceValue = game.secondPlayer().first()
        )
    )
    activityMainBinding.playerTwoMiddleDiceImageId.setImageResource(
        list.getDiceImageFromDiceValue(
            diceValue = game.secondPlayer().middleDice()
        )
    )
    activityMainBinding.playerTwoLastDiceImageId.setImageResource(
        list.getDiceImageFromDiceValue(
            diceValue = game.secondPlayer().last()
        )
    )
}

fun throwDiceAnimation(
    diceImage: ImageView,
    diceValue: Int
) = diceImage.apply {
    setImageResource(
        diceImages.getDiceImageFromDiceValue(
            diceValue = diceValue
        )
    )
}.run {
    startAnimation(RotateAnimation(
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
