package zlc.season.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_android_action_menu.*
import zlc.season.rxrouter.RxRouter
import zlc.season.rxrouterannotation.Uri

@Uri(androidActionMenu)
class AndroidActionMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_action_menu)

        androidAction.setOnClickListener {
            RxRouter.of(this)
                    .routeAction("")
                    .subscribe({
                        "result".toast()
                    }, {
                        it.message?.toast()
                    })
        }

        androidActionForResult.setOnClickListener {
            RxRouter.of(this)
                    .routeAction("")
                    .subscribe({
                        "result".toast()
                    }, {
                        it.message?.toast()
                    })
        }
    }
}
