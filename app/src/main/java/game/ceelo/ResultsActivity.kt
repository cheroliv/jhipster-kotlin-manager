@file:Suppress("MemberVisibilityCanBePrivate")

package game.ceelo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import game.ceelo.databinding.ActivityStatsBinding.inflate
import org.koin.android.ext.android.get

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate(layoutInflater).apply {
            setContentView(root)
            backButton.apply {
                setOnClickListener { finish() }
            }
            statsRV.apply {
                adapter = GamesAdapter(get<GameService>().allGames())
                layoutManager = LinearLayoutManager(this@ResultsActivity)
            }
        }
    }

}

