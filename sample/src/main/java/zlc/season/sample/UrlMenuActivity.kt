package zlc.season.sample

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_url_menu.*
import zlc.season.rxrouter.RxRouter
import zlc.season.rxrouterannotation.Url

@Url(urlMenu)
class UrlMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url_menu)

        url.setOnClickListener {
            RxRouter.of(this)
                    .route("this is a url")
                    .subscribe({
                        "no result".toast()
                    })
        }

        url_for_result.setOnClickListener {
            RxRouter.of(this.applicationContext)
                    .with(10)
                    .with(true)
                    .with(20.12)
                    .with("this is a string value")
                    .with(Bundle())
                    .route("this is another url")
                    .subscribe {
                        if (it.resultCode == Activity.RESULT_OK) {
                            val intent = it.data
                            val stringResult = intent.getStringExtra("result")
                            result_text.text = stringResult
                            stringResult.toast()
                        }
                    }
        }

        library_url_for_result.setOnClickListener {
            RxRouter.of(this)
                    .with(2000)
                    .with(9999999999999999)
                    .with(false)
                    .with(0.00001)
                    .with("this is a string value")
                    .route("this is library url")
                    .subscribe {
                        if (it.resultCode == Activity.RESULT_OK) {
                            val intent = it.data
                            val stringResult = intent.getStringExtra("result")
                            result_text.text = stringResult
                            stringResult.toast()
                        }
                    }
        }
    }
}
