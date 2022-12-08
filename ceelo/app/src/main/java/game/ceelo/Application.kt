package game.ceelo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module


val ceeloModule = module {
    single<CeeloService> { CeeloServiceInMemory() }
}

val ceeloServiceInMem: CeeloService by lazy {
    CeeloServiceInMemory()
}

class CeeLoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CeeLoApplication)
            modules(ceeloModule)
        }
    }
}


