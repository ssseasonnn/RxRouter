package zlc.season.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import zlc.season.rxrouter.RxRouter
import zlc.season.rxrouterannotation.Uri
import zlc.season.samplelibrary.libraryBar
import zlc.season.samplelibrary.libraryFoo


@Uri("main_activity")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uri.setOnClickListener {
            RxRouter.of(this)
                    .route("this is a uri")
                    .subscribe()
        }

        action.setOnClickListener {
            RxRouter.of(this)
                    .route(libraryFoo)
                    .subscribe()
        }

        clazz.setOnClickListener {
            RxRouter.of(this)
                    .route(libraryBar)
                    .subscribe()
        }

        button4.setOnClickListener {

        }
    }
}
