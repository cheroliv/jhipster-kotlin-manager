package game.ceelo

import android.os.Bundle
import android.view.View.VISIBLE
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.ImageView
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
            laodLocalGame(binding = this)
        }
    }
}

private fun throwDiceAnimation(diceImage: ImageView, diceValue: Int) {
    diceImage.apply {
        setImageResource(getDiceImageResourcefromDiceValue(diceValue))
        startAnimation(RotateAnimation(
            0f,
            360f,
            RELATIVE_TO_SELF,
            0.5f,
            RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = 500
        })
    }

}

private fun getDiceImageResourcefromDiceValue(diceValue: Int): Int {
    return when (diceValue) {
        ONE -> dice_face_one
        TWO -> dice_face_two
        THREE -> dice_face_three
        FOUR -> dice_face_four
        FIVE -> dice_face_five
        SIX -> dice_face_six
        else -> throw Exception("Only six faces is possible!")
    }
}

private fun laodLocalGame(binding: ActivityMainBinding) {
    binding.apply {
        playLocalButton.setOnClickListener {
            dicesThrow.apply player@{
                dicesThrow.apply computer@{
                    throwDiceAnimation(playerOneFirstDiceImageId, this@player.first())
                    throwDiceAnimation(playerOneMiddleDiceImageId, this@player.middle())
                    throwDiceAnimation(playerOneLastDiceImageId, this@player.last())
                    throwDiceAnimation(playerTwoFirstDiceImageId, this@computer.first())
                    throwDiceAnimation(playerTwoMiddleDiceImageId, this@computer.middle())
                    throwDiceAnimation(playerTwoLastDiceImageId, this@computer.last())
                    localPlayerResultText.apply {
                        text =
                            when (this@player.compareThrows(secondPlayerThrow = this@computer)) {
                                WIN -> WIN.toString()
                                LOOSE -> LOOSE.toString()
                                else -> RETHROW.toString()
                            }
                        visibility = VISIBLE
                    }
                    computerResultText.apply {
                        text =
                            when (this@computer.compareThrows(secondPlayerThrow = this@player)) {
                                WIN -> WIN.toString()
                                LOOSE -> LOOSE.toString()
                                else -> RETHROW.toString()
                            }
                        visibility = VISIBLE
                    }
                }
            }

        }
    }
}