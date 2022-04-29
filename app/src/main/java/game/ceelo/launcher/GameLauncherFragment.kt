package game.ceelo.launcher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import game.ceelo.R

class GameLauncherFragment : Fragment() {

    companion object {
        fun newInstance() = GameLauncherFragment()
    }

    private lateinit var viewModel: GameLauncherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_launcher_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GameLauncherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}