package game.ceelo

import android.app.Application
import android.util.Log
import game.ceelo.inmemory.CeeloServiceInMemory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module


class CeeLoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(this::class.java.name, "$this.onCreate()")
        startKoin {
            androidLogger()
            androidContext(this@CeeLoApplication)
            modules(ceeloModule)
        }
    }
}

@JvmField
val ceeloModule = module {
    single<CeeloService> { CeeloServiceInMemory() }
}