package game.ceelo

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module


@JvmField
val ceeloModule = module {
    single<CeeloService> { CeeloServiceInMemory() }
}

class CeeLoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        Log.d(this::class.java.name, "$this.onCreate()")
        startKoin {
            androidLogger()
            androidContext(this@CeeLoApplication)
            modules(ceeloModule)
        }
    }
}


