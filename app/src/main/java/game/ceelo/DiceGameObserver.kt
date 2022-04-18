package game.ceelo

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class DiceGameObserver:DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d(
            DiceGameObserver::class.java.name,
            "onCreate()"
        )
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d(
            DiceGameObserver::class.java.name,
            "onStart()"
        )
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d(
            DiceGameObserver::class.java.name,
            "onResume()"
        )
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d(
            DiceGameObserver::class.java.name,
            "onPause()"
        )
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d(
            DiceGameObserver::class.java.name,
            "onStop()"
        )
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d(
            DiceGameObserver::class.java.name,
            "onDestroy()"
        )
    }
}