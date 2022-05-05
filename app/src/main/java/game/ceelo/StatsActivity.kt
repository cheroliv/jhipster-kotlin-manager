package game.ceelo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import game.ceelo.databinding.ActivityStatsBinding.inflate
import game.ceelo.service.ICeeloService
import game.ceelo.service.local.inmemory.CeeloServiceInMemory
import game.ceelo.stats.CeeloAdapter
import game.ceelo.vm.DiceGameViewModel

val ceeloService: ICeeloService = CeeloServiceInMemory(null)

class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val diceGameViewModel = ViewModelProvider(this)
//            .get(DiceGameViewModel::class.java)
        inflate(layoutInflater).apply {
            setContentView(root)
            backButton.setOnClickListener { finish() }
            Log.d("foo", "avant")
            statsRV.apply {
                Log.d("foo", "pendant")
//                diceGameViewModel.games.observe(this@StatsActivity) { g ->
//                    games.clear()
//                    games.addAll(g)
//                    Log.d("foo", "on observe")
//                    adapter = CeeloAdapter(games = games)
//                    Log.d("foo", g.toString())
//                }
                adapter = CeeloAdapter(ceeloService.allGames())

                layoutManager = LinearLayoutManager(this@StatsActivity)
            }
            Log.d("foo", "apres")
        }
    }
}
