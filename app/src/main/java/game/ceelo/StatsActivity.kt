package game.ceelo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import game.ceelo.R.id.statsRV
import game.ceelo.service.local.inmemory.CeeloServiceInMemory
import game.ceelo.stats.CeeloAdapter

class StatsActivity : AppCompatActivity() {

    fun onClickBackButtonEvent(view: View?): Unit {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        mViewModel.mPersons.observe(
                this, persons -> {
                    personsData.clear();
                    personsData.addAll(persons);
                    if (personAdapter == null) {
                        personAdapter = new PersonAdapter(personsData,
                                this);
                        recyclerView = findViewById(rcView);
                        recyclerView.setAdapter(personAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    } else personAdapter.notifyDataSetChanged();
                }
        );
 */



//        findViewById<RecyclerView>(statsRV).apply {
//            Log.d("foo",)
//            adapter = CeeloAdapter(CeeloServiceInMemory(null).allGames())
//            layoutManager = LinearLayoutManager(this@StatsActivity)
//        }
    }
}
