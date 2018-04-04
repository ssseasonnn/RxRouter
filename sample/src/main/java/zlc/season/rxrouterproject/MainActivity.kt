package zlc.season.rxrouterproject

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Maybe


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Maybe.just("zlc.season.test1_activity")
                .map {
                    val target = MainModuleProvider().parseUri(it)
                    return@map Intent(this@MainActivity, target)
                }
                .subscribe {
                    startActivity(it)
                }

    }
}
