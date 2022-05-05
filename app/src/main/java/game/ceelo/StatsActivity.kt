package game.ceelo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import game.ceelo.databinding.ActivityStatsBinding.inflate

class StatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate(layoutInflater).apply {
            setContentView(root)
            backButton.setOnClickListener { finish() }
        }
    }
}
