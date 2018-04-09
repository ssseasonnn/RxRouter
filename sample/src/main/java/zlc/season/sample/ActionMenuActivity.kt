package zlc.season.sample

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_action_menu.*
import zlc.season.rxrouter.RxRouter
import zlc.season.rxrouterannotation.Uri

@Uri(actionMenu)
class ActionMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_menu)

        action.setOnClickListener {
            RxRouter.of(this)
                    .routeAction("zlc.season.sample.action")
                    .subscribe({
                        "no result".toast()
                    }, {
                        it.message?.toast()
                    })
        }

        action_for_result.setOnClickListener {
            RxRouter.of(this)
                    .with(10)
                    .with(true)
                    .with(20.12)
                    .with("this is a string value")
                    .routeAction("zlc.season.sample.action_for_result")
                    .subscribe({
                        if (it.resultCode == Activity.RESULT_OK) {
                            val intent = it.data
                            val stringResult = intent.getStringExtra("result")
                            result_text.text = stringResult
                            stringResult.toast()
                        }
                    }, {
                        it.message?.toast()
                    })
        }

        library_action_for_result.setOnClickListener {
            RxRouter.of(this)
                    .with(2000)
                    .with(9999999999999999)
                    .with(false)
                    .with(0.00001)
                    .with("this is a string value")
                    .routeAction("zlc.season.samplelibrary.action_for_result")
                    .subscribe({
                        if (it.resultCode == Activity.RESULT_OK) {
                            val intent = it.data
                            val stringResult = intent.getStringExtra("result")
                            result_text.text = stringResult
                            stringResult.toast()
                        }
                    }, {
                        it.message?.toast()
                    })
        }
    }
}
