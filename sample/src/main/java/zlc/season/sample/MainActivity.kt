package zlc.season.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import zlc.season.rxrouter.RxRouter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        url.setOnClickListener {
            RxRouter.of(this)
                    .route(urlMenu)
                    .subscribe()
        }

        action.setOnClickListener {
            RxRouter.of(this)
                    .route(actionMenu)
                    .subscribe()
        }

        clazz.setOnClickListener {
            RxRouter.of(this)
                    .route(clazzMenu)
                    .subscribe()
        }

        android_action.setOnClickListener {
            RxRouter.of(this)
                    .route(androidActionMenu)
                    .subscribe()
        }

        firewall.setOnClickListener {
            RxRouter.of(this)
                    .route(firewallMenu)
                    .subscribe()
        }
    }
}
