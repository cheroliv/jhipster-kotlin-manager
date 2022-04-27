package game.ceelo

import android.os.Bundle
import android.view.View.VISIBLE
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import game.ceelo.R.drawable.*
import game.ceelo.databinding.ActivityMainBinding
import game.ceelo.databinding.ActivityMainBinding.inflate
import game.ceelo.domain.*
import game.ceelo.domain.DiceThrowResult.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate(layoutInflater).apply {
            setContentView(root)
            loadLocalGame(binding = this)
        }
    }
}

fun loadLocalGame(binding: ActivityMainBinding) = binding.apply {
    playLocalButton.setOnClickListener {
        dicesThrow.apply player@{
            dicesThrow.apply computer@{

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