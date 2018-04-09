package zlc.season.sample

import android.app.Application
import android.widget.Toast
import zlc.season.rxrouter.RxRouterProviders
import zlc.season.samplelibrary.LibraryRouterProvider

class CustomApplication : Application() {
    companion object {
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        RxRouterProviders.add(MainRouterProvider(), LibraryRouterProvider())
    }
}

fun Any.toast() {
    Toast.makeText(CustomApplication.app, this.toString(), Toast.LENGTH_SHORT).show()
}