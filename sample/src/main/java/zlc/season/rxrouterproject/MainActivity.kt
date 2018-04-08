package zlc.season.rxrouterproject

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

        button1.setOnClickListener {
            RxRouter.of(this)
                    .route("test1_activity")
                    .subscribe()
        }

        button2.setOnClickListener {
            RxRouter.of(this)
                    .route(libraryFoo)
                    .subscribe()
        }

        button3.setOnClickListener {
            RxRouter.of(this)
                    .route(libraryBar)
                    .subscribe()
        }

        button4.setOnClickListener {

        }
    }
}
