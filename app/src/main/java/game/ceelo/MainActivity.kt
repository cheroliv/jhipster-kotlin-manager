package game.ceelo

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import game.ceelo.R.drawable.*
import game.ceelo.databinding.ActivityMainBinding
import game.ceelo.databinding.ActivityMainBinding.inflate
import game.ceelo.domain.*
import game.ceelo.domain.DiceThrowResult.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        laodLocalGame()

    }

    private fun laodLocalGame() {
        binding.playLocalButton.setOnClickListener {
            val player: List<Int> = dicesThrow
            val computer: List<Int> = dicesThrow

            binding.playerOneFirstDiceImageId.setImageResource(
                getDiceImageResourcefromDiceValue(player.first())
            )

            binding.playerTwoMiddleDiceImageId.setImageResource(
                getDiceImageResourcefromDiceValue(player.middle())
            )
            binding.playerTwoLastDiceImageId.setImageResource(
                getDiceImageResourcefromDiceValue(player.last())
            )
            binding.playerTwoFirstDiceImageId.setImageResource(
                getDiceImageResourcefromDiceValue(computer.first())
            )
            binding.playerTwoMiddleDiceImageId.setImageResource(
                getDiceImageResourcefromDiceValue(computer.middle())
            )
            binding.playerTwoLastDiceImageId.setImageResource(
                getDiceImageResourcefromDiceValue(computer.last())
            )

            binding.localPlayerResultText.apply {
                text =
                    when (player.compareThrows(computer)) {
                        WIN -> WIN.toString()
                        LOOSE -> LOOSE.toString()
                        else -> RETHROW.toString()
                    }
                visibility = VISIBLE
            }
            binding.computerResultText.apply {
                text =
                    when (computer.compareThrows(player)) {
                        WIN -> WIN.toString()
                        LOOSE -> LOOSE.toString()
                        else -> RETHROW.toString()
                    }
                visibility = VISIBLE
            }
        }
    }

    fun getDiceImageResourcefromDiceValue(diceValue: Int): Int {
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
}