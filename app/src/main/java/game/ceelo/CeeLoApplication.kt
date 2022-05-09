package game.ceelo

import android.app.Application
import android.util.Log
import game.ceelo.service.CeeloServiceInMemory
import game.ceelo.service.ICeeloService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent

//val ceeloService: ICeeloService by KoinJavaComponent.inject(ICeeloService::class.java)

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
            modules(modules = module {
                single<ICeeloService> { CeeloServiceInMemory() }
            })
        }
    }
}