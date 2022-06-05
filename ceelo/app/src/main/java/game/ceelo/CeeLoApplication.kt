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
        Log.d(this::class.java.name, "$this.onCreate()")
        startKoin {
            androidLogger()
            androidContext(this@CeeLoApplication)
            modules(modules = ceeloModule)
        }
    }
}

@JvmField
val ceeloModule = module {
    single<CeeloService> { CeeloServiceInMemory() }
}

val diceImages: List<Int>
    get() = listOf(
        dice_face_one,
        dice_face_two,
        dice_face_three,
        dice_face_four,
        dice_face_five,
        dice_face_six,
    )

fun ActivityMainBinding.loadLocalGame(
    activity: MainActivity
): ActivityMainBinding = apply {
    val diceGameViewModel = ViewModelProvider(activity)
        .get(DiceGameViewModel::class.java)
    diceGameViewModel.diceGame.observe(activity) { game ->
        diceImages.run {
            playerOneUI(
                game = game,
                diceImages = this
            )
            playerTwoUI(
                game = game,
                diceImages = this
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
                            list = this@player,
                            diceGameViewModel = this@vm
                        )
                        playerTwoThrow(
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

fun ActivityMainBinding.playerOneThrow(
    list: List<Int>,
    diceGameViewModel: DiceGameViewModel
) {
    runDiceAnimation(
        diceImage = playerOneFirstDiceImageId,
        diceValue = list.first()
    )
    runDiceAnimation(
        diceImage = playerOneMiddleDiceImageId,
        diceValue = list.middleDice()
    )
    runDiceAnimation(
        diceImage = playerOneLastDiceImageId,
        diceValue = list.last()
    )
    setTextViewResult(
        textViewResult = localPlayerResultText,
        diceResult = diceGameViewModel.playerOneResult.value!!,
        textViewVisibility = diceGameViewModel.resultVisibility.value!!
    )
}

fun ActivityMainBinding.playerTwoThrow(
    list: List<Int>,
    diceGameViewModel: DiceGameViewModel
) {
    runDiceAnimation(
        diceImage = playerTwoFirstDiceImageId,
        diceValue = list.first()
    )
    runDiceAnimation(
        diceImage = playerTwoMiddleDiceImageId,
        diceValue = list.middleDice()
    )
    runDiceAnimation(
        diceImage = playerTwoLastDiceImageId,
        diceValue = list.last()
    )
    setTextViewResult(
        textViewResult = computerResultText,
        diceResult = diceGameViewModel.playerTwoResult.value!!,
        textViewVisibility = diceGameViewModel.resultVisibility.value!!
    )
}

fun ActivityMainBinding.playerOneUI(
    game: List<List<Int>>,
    diceImages: List<Int>
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
