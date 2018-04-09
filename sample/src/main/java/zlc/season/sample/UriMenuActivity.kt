package zlc.season.sample

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_uri_menu.*
import zlc.season.rxrouter.RxRouter
import zlc.season.rxrouterannotation.Uri

@Uri(uriMenuActivity)
class UriMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uri_menu)

        uri.setOnClickListener {
            RxRouter.of(this)
                    .route("this is a uri")
                    .subscribe({
                        "no result".toast()
                    })
        }

        uri_for_result.setOnClickListener {
            RxRouter.of(this)
                    .with(10)
                    .with(true)
                    .with(20.12)
                    .with("this is a string value")
                    .route("this is another uri")
                    .subscribe {
                        if (it.resultCode == Activity.RESULT_OK) {
                            val intent = it.data
                            val stringResult = intent.getStringExtra("result")
                            result_text.text = stringResult
                            stringResult.toast()
                        }
                    }
        }

        library_uri_for_result.setOnClickListener {
            RxRouter.of(this)
                    .with(2000)
                    .with(9999999999999999)
                    .with(false)
                    .with(0.00001)
                    .with("this is a string value")
                    .route("this is library uri")
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
