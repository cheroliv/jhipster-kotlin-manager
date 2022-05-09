package game.ceelo

import android.app.Application
import android.util.Log
import game.ceelo.R.drawable.*
import game.ceelo.service.CeeloServiceInMemory
import game.ceelo.service.ICeeloService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

val diceImages: List<Int> by lazy {
    listOf(
        dice_face_one,
        dice_face_two,
        dice_face_three,
        dice_face_four,
        dice_face_five,
        dice_face_six,
    )
}

class CeeLoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        this::class.java.name.apply {
            Log.d(
                this,
                "$this.onCreate()"
            )
        }
        startKoin {
            androidLogger()
            androidContext(this@CeeLoApplication)
            modules(modules = ceeloModule)
        }
    }
}

val ceeloModule = module {
    single<ICeeloService> { CeeloServiceInMemory() }
}