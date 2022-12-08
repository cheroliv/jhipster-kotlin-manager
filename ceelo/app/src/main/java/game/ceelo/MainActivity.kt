package game.ceelo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import game.ceelo.databinding.ActivityMainBinding.inflate


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate(layoutInflater).apply {
            setContentView(root)
            loadLocalGame(
                mainActivity = this@MainActivity,
                ViewModelProvider(this@MainActivity)[DiceGameViewModel::class.java],
                playersUI = listOf(
                    listOf(
                        playerOneFirstDiceImageId,
                        playerOneMiddleDiceImageId,
                        playerOneLastDiceImageId
                    ),
                    listOf(
                        playerTwoFirstDiceImageId,
                        playerTwoMiddleDiceImageId,
                        playerTwoLastDiceImageId
                    )
                ),
                resultUI = listOf(
                    localPlayerResultText,
                    computerResultText
                )
            )
        }
    }
}