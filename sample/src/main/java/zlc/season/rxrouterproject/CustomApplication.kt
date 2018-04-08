package zlc.season.rxrouterproject

import android.app.Application
import zlc.season.rxrouter.RxRouterProviders
import zlc.season.samplelibrary.LibraryRouterProvider

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RxRouterProviders.add(MainRouterProvider(), LibraryRouterProvider())
    }
}