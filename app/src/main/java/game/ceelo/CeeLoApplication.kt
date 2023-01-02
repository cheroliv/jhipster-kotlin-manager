package game.ceelo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


class CeeLoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CeeLoApplication)
            modules(module {
                singleOf(::GameServiceAndroid) { bind<GameService>() }
                viewModelOf(::DiceGameViewModel)
            })
        }
    }
}

