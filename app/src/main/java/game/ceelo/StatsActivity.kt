package game.ceelo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import game.ceelo.databinding.ActivityStatsBinding.inflate
import game.ceelo.service.CeeloServiceInMemory
import game.ceelo.stats.CeeloAdapter
import game.ceelo.vm.DiceGameViewModel


class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelProvider(owner = this).get(DiceGameViewModel::class.java).run {
            inflate(layoutInflater).apply {
                setContentView(root)
                backButton.setOnClickListener { finish() }
                Log.d("foo", "avant")

                statsRV.apply {
                    Log.d("foo", "pendant")
                    games.observe(this@StatsActivity) { games: List<List<List<Int>>> ->
                        Log.d("foo", "on observe")
//                    adapter = CeeloAdapter(games = games)
                        Log.d("foo", games.toString())
                    }
                    val ceeloService = CeeloServiceInMemory()
                    adapter = CeeloAdapter(ceeloService.allGames())
                    layoutManager = LinearLayoutManager(this@StatsActivity)
                }

                Log.d("foo", "apres")
            }
        }

    }
}
