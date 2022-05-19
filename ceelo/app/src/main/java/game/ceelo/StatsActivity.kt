package game.ceelo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import game.ceelo.databinding.ActivityStatsBinding.inflate
import game.ceelo.inmemory.ceeloService
import game.ceelo.stats.CeeloAdapter


class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate(layoutInflater).apply {
            setContentView(root)
            backButton.setOnClickListener { finish() }
            statsRV.apply {
                adapter = CeeloAdapter(ceeloService.allGames())
                layoutManager = LinearLayoutManager(this@StatsActivity)
            }
        }
    }

}
