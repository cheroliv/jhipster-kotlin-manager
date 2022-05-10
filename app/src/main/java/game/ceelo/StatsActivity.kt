package game.ceelo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import game.ceelo.databinding.ActivityStatsBinding.inflate
import game.ceelo.domain.ceeloService
import game.ceelo.service.CeeloServiceInMemory
import game.ceelo.stats.CeeloAdapter
import game.ceelo.vm.DiceGameViewModel


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

