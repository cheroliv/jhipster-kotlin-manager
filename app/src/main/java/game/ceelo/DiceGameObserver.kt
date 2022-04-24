package game.ceelo

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class DiceGameObserver : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        this::class.java.name.apply {
            Log.d(
                this,
                "$this.onCreate()"
            )
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        this::class.java.name.apply {
            Log.d(
                this,
                "$this.onStart()"
            )
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        this::class.java.name.apply {
            Log.d(
                this,
                "$this.onResume()"
            )
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        this::class.java.name.apply {
            Log.d(
                this,
                "$this.onPause()"
            )
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        this::class.java.name.apply {
            Log.d(
                this,
                "$this.onStop()"
            )
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        this::class.java.name.apply {
            Log.d(
                this,
                "$this.onDestroy()"
            )
        }
    }
}